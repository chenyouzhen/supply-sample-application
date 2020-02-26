import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IObservation, Observation } from 'app/shared/model/observation.model';
import { ObservationService } from './observation.service';
import { IAssemblyLine } from 'app/shared/model/assembly-line.model';
import { AssemblyLineService } from 'app/entities/assembly-line/assembly-line.service';
import { ILink } from 'app/shared/model/link.model';
import { LinkService } from 'app/entities/link/link.service';

type SelectableEntity = IAssemblyLine | ILink;

@Component({
  selector: 'jhi-observation-update',
  templateUrl: './observation-update.component.html'
})
export class ObservationUpdateComponent implements OnInit {
  isSaving = false;
  assemblylines: IAssemblyLine[] = [];
  links: ILink[] = [];

  editForm = this.fb.group({
    id: [],
    phenomenonTime: [],
    result: [null, [Validators.required]],
    resultTime: [null, [Validators.required]],
    property: [null, [Validators.required]],
    unitOfMeasurement: [],
    intervalTime: [],
    reserve: [],
    assemblylineId: [null, Validators.required],
    linkId: [null, Validators.required]
  });

  constructor(
    protected observationService: ObservationService,
    protected assemblyLineService: AssemblyLineService,
    protected linkService: LinkService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observation }) => {
      if (!observation.id) {
        const today = moment().startOf('day');
        observation.phenomenonTime = today;
        observation.resultTime = today;
      }

      this.updateForm(observation);

      this.assemblyLineService.query().subscribe((res: HttpResponse<IAssemblyLine[]>) => (this.assemblylines = res.body || []));

      this.linkService.query().subscribe((res: HttpResponse<ILink[]>) => (this.links = res.body || []));
    });
  }

  updateForm(observation: IObservation): void {
    this.editForm.patchValue({
      id: observation.id,
      phenomenonTime: observation.phenomenonTime ? observation.phenomenonTime.format(DATE_TIME_FORMAT) : null,
      result: observation.result,
      resultTime: observation.resultTime ? observation.resultTime.format(DATE_TIME_FORMAT) : null,
      property: observation.property,
      unitOfMeasurement: observation.unitOfMeasurement,
      intervalTime: observation.intervalTime,
      reserve: observation.reserve,
      assemblylineId: observation.assemblylineId,
      linkId: observation.linkId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const observation = this.createFromForm();
    if (observation.id !== undefined) {
      this.subscribeToSaveResponse(this.observationService.update(observation));
    } else {
      this.subscribeToSaveResponse(this.observationService.create(observation));
    }
  }

  private createFromForm(): IObservation {
    return {
      ...new Observation(),
      id: this.editForm.get(['id'])!.value,
      phenomenonTime: this.editForm.get(['phenomenonTime'])!.value
        ? moment(this.editForm.get(['phenomenonTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      result: this.editForm.get(['result'])!.value,
      resultTime: this.editForm.get(['resultTime'])!.value ? moment(this.editForm.get(['resultTime'])!.value, DATE_TIME_FORMAT) : undefined,
      property: this.editForm.get(['property'])!.value,
      unitOfMeasurement: this.editForm.get(['unitOfMeasurement'])!.value,
      intervalTime: this.editForm.get(['intervalTime'])!.value,
      reserve: this.editForm.get(['reserve'])!.value,
      assemblylineId: this.editForm.get(['assemblylineId'])!.value,
      linkId: this.editForm.get(['linkId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservation>>): void {
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
