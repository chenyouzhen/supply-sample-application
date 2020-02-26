import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILink, Link } from 'app/shared/model/link.model';
import { LinkService } from './link.service';

@Component({
  selector: 'jhi-link-update',
  templateUrl: './link-update.component.html'
})
export class LinkUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    deviceId: [null, [Validators.required]],
    description: []
  });

  constructor(protected linkService: LinkService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ link }) => {
      this.updateForm(link);
    });
  }

  updateForm(link: ILink): void {
    this.editForm.patchValue({
      id: link.id,
      name: link.name,
      deviceId: link.deviceId,
      description: link.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const link = this.createFromForm();
    if (link.id !== undefined) {
      this.subscribeToSaveResponse(this.linkService.update(link));
    } else {
      this.subscribeToSaveResponse(this.linkService.create(link));
    }
  }

  private createFromForm(): ILink {
    return {
      ...new Link(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILink>>): void {
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
}
