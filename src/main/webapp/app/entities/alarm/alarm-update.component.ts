import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAlarm, Alarm } from 'app/shared/model/alarm.model';
import { AlarmService } from './alarm.service';
import { IFactory } from 'app/shared/model/factory.model';
import { FactoryService } from 'app/entities/factory/factory.service';

@Component({
  selector: 'jhi-alarm-update',
  templateUrl: './alarm-update.component.html'
})
export class AlarmUpdateComponent implements OnInit {
  isSaving = false;
  factories: IFactory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    phenomenonTime: [],
    result: [null, [Validators.required]],
    resultTime: [null, [Validators.required]],
    description: [],
    level: [],
    status: [null, [Validators.required]],
    resolveTime: [],
    details: [],
    factoryId: []
  });

  constructor(
    protected alarmService: AlarmService,
    protected factoryService: FactoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alarm }) => {
      if (!alarm.id) {
        const today = moment().startOf('day');
        alarm.phenomenonTime = today;
        alarm.resultTime = today;
        alarm.resolveTime = today;
      }

      this.updateForm(alarm);

      this.factoryService.query().subscribe((res: HttpResponse<IFactory[]>) => (this.factories = res.body || []));
    });
  }

  updateForm(alarm: IAlarm): void {
    this.editForm.patchValue({
      id: alarm.id,
      name: alarm.name,
      phenomenonTime: alarm.phenomenonTime ? alarm.phenomenonTime.format(DATE_TIME_FORMAT) : null,
      result: alarm.result,
      resultTime: alarm.resultTime ? alarm.resultTime.format(DATE_TIME_FORMAT) : null,
      description: alarm.description,
      level: alarm.level,
      status: alarm.status,
      resolveTime: alarm.resolveTime ? alarm.resolveTime.format(DATE_TIME_FORMAT) : null,
      details: alarm.details,
      factoryId: alarm.factoryId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alarm = this.createFromForm();
    if (alarm.id !== undefined) {
      this.subscribeToSaveResponse(this.alarmService.update(alarm));
    } else {
      this.subscribeToSaveResponse(this.alarmService.create(alarm));
    }
  }

  private createFromForm(): IAlarm {
    return {
      ...new Alarm(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      phenomenonTime: this.editForm.get(['phenomenonTime'])!.value
        ? moment(this.editForm.get(['phenomenonTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      result: this.editForm.get(['result'])!.value,
      resultTime: this.editForm.get(['resultTime'])!.value ? moment(this.editForm.get(['resultTime'])!.value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description'])!.value,
      level: this.editForm.get(['level'])!.value,
      status: this.editForm.get(['status'])!.value,
      resolveTime: this.editForm.get(['resolveTime'])!.value
        ? moment(this.editForm.get(['resolveTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      details: this.editForm.get(['details'])!.value,
      factoryId: this.editForm.get(['factoryId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlarm>>): void {
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
