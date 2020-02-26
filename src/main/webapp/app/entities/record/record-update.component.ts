import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRecord, Record } from 'app/shared/model/record.model';
import { RecordService } from './record.service';
import { IAssemblyLine } from 'app/shared/model/assembly-line.model';
import { AssemblyLineService } from 'app/entities/assembly-line/assembly-line.service';

@Component({
  selector: 'jhi-record-update',
  templateUrl: './record-update.component.html'
})
export class RecordUpdateComponent implements OnInit {
  isSaving = false;
  assemblylines: IAssemblyLine[] = [];

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
    assemblylineId: [null, Validators.required]
  });

  constructor(
    protected recordService: RecordService,
    protected assemblyLineService: AssemblyLineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ record }) => {
      if (!record.id) {
        const today = moment().startOf('day');
        record.phenomenonTime = today;
        record.resultTime = today;
      }

      this.updateForm(record);

      this.assemblyLineService.query().subscribe((res: HttpResponse<IAssemblyLine[]>) => (this.assemblylines = res.body || []));
    });
  }

  updateForm(record: IRecord): void {
    this.editForm.patchValue({
      id: record.id,
      name: record.name,
      phenomenonTime: record.phenomenonTime ? record.phenomenonTime.format(DATE_TIME_FORMAT) : null,
      result: record.result,
      resultTime: record.resultTime ? record.resultTime.format(DATE_TIME_FORMAT) : null,
      description: record.description,
      intervalTime: record.intervalTime,
      type: record.type,
      unitOfMeasurement: record.unitOfMeasurement,
      reserve: record.reserve,
      assemblylineId: record.assemblylineId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const record = this.createFromForm();
    if (record.id !== undefined) {
      this.subscribeToSaveResponse(this.recordService.update(record));
    } else {
      this.subscribeToSaveResponse(this.recordService.create(record));
    }
  }

  private createFromForm(): IRecord {
    return {
      ...new Record(),
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
      assemblylineId: this.editForm.get(['assemblylineId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecord>>): void {
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

  trackById(index: number, item: IAssemblyLine): any {
    return item.id;
  }
}
