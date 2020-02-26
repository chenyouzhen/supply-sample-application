import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { IFactory } from 'app/shared/model/factory.model';
import { FactoryService } from 'app/entities/factory/factory.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  factories: IFactory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    type: [null, [Validators.required]],
    description: [],
    capacity: [],
    planCapacity: [],
    reserve: [],
    factoryId: [null, Validators.required]
  });

  constructor(
    protected productService: ProductService,
    protected factoryService: FactoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      if (!product.id) {
        const today = moment().startOf('day');
        product.capacity = today;
        product.planCapacity = today;
      }

      this.updateForm(product);

      this.factoryService.query().subscribe((res: HttpResponse<IFactory[]>) => (this.factories = res.body || []));
    });
  }

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      type: product.type,
      description: product.description,
      capacity: product.capacity ? product.capacity.format(DATE_TIME_FORMAT) : null,
      planCapacity: product.planCapacity ? product.planCapacity.format(DATE_TIME_FORMAT) : null,
      reserve: product.reserve,
      factoryId: product.factoryId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      description: this.editForm.get(['description'])!.value,
      capacity: this.editForm.get(['capacity'])!.value ? moment(this.editForm.get(['capacity'])!.value, DATE_TIME_FORMAT) : undefined,
      planCapacity: this.editForm.get(['planCapacity'])!.value
        ? moment(this.editForm.get(['planCapacity'])!.value, DATE_TIME_FORMAT)
        : undefined,
      reserve: this.editForm.get(['reserve'])!.value,
      factoryId: this.editForm.get(['factoryId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IFactory): any {
    return item.id;
  }
}
