import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IKpi, Kpi } from 'app/shared/model/kpi.model';
import { KpiService } from './kpi.service';
import { IFactory } from 'app/shared/model/factory.model';
import { FactoryService } from 'app/entities/factory/factory.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IFactory | IProduct;

@Component({
  selector: 'jhi-kpi-update',
  templateUrl: './kpi-update.component.html'
})
export class KpiUpdateComponent implements OnInit {
  isSaving = false;
  factories: IFactory[] = [];
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    phenomenonTime: [],
    result: [null, [Validators.required]],
    resultTime: [null, [Validators.required]],
    description: [],
    intervalTime: [],
    type: [null, [Validators.required]],
    unitOfMeasurement: [],
    reserve: [],
    factoryId: [null, Validators.required],
    productId: [null, Validators.required]
  });

  constructor(
    protected kpiService: KpiService,
    protected factoryService: FactoryService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kpi }) => {
      if (!kpi.id) {
        const today = moment().startOf('day');
        kpi.phenomenonTime = today;
        kpi.resultTime = today;
      }

      this.updateForm(kpi);

      this.factoryService.query().subscribe((res: HttpResponse<IFactory[]>) => (this.factories = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(kpi: IKpi): void {
    this.editForm.patchValue({
      id: kpi.id,
      name: kpi.name,
      phenomenonTime: kpi.phenomenonTime ? kpi.phenomenonTime.format(DATE_TIME_FORMAT) : null,
      result: kpi.result,
      resultTime: kpi.resultTime ? kpi.resultTime.format(DATE_TIME_FORMAT) : null,
      description: kpi.description,
      intervalTime: kpi.intervalTime,
      type: kpi.type,
      unitOfMeasurement: kpi.unitOfMeasurement,
      reserve: kpi.reserve,
      factoryId: kpi.factoryId,
      productId: kpi.productId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kpi = this.createFromForm();
    if (kpi.id !== undefined) {
      this.subscribeToSaveResponse(this.kpiService.update(kpi));
    } else {
      this.subscribeToSaveResponse(this.kpiService.create(kpi));
    }
  }

  private createFromForm(): IKpi {
    return {
      ...new Kpi(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      phenomenonTime: this.editForm.get(['phenomenonTime'])!.value
        ? moment(this.editForm.get(['phenomenonTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      result: this.editForm.get(['result'])!.value,
      resultTime: this.editForm.get(['resultTime'])!.value ? moment(this.editForm.get(['resultTime'])!.value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description'])!.value,
      intervalTime: this.editForm.get(['intervalTime'])!.value,
      type: this.editForm.get(['type'])!.value,
      unitOfMeasurement: this.editForm.get(['unitOfMeasurement'])!.value,
      reserve: this.editForm.get(['reserve'])!.value,
      factoryId: this.editForm.get(['factoryId'])!.value,
      productId: this.editForm.get(['productId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKpi>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
