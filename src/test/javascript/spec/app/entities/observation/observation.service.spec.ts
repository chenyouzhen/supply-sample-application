import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ObservationService } from 'app/entities/observation/observation.service';
import { IObservation, Observation } from 'app/shared/model/observation.model';

describe('Service Tests', () => {
  describe('Observation Service', () => {
    let injector: TestBed;
    let service: ObservationService;
    let httpMock: HttpTestingController;
    let elemDefault: IObservation;
    let expectedResult: IObservation | IObservation[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ObservationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Observation(0, currentDate, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            phenomenonTime: currentDate.format(DATE_TIME_FORMAT),
            resultTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Observation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            phenomenonTime: currentDate.format(DATE_TIME_FORMAT),
            resultTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            phenomenonTime: currentDate,
            resultTime: currentDate
          },
          returnedFromService
        );

        service.create(new Observation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Observation', () => {
        const returnedFromService = Object.assign(
          {
            phenomenonTime: currentDate.format(DATE_TIME_FORMAT),
            result: 'BBBBBB',
            resultTime: currentDate.format(DATE_TIME_FORMAT),
            property: 'BBBBBB',
            unitOfMeasurement: 'BBBBBB',
            intervalTime: 1,
            reserve: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            phenomenonTime: currentDate,
            resultTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Observation', () => {
        const returnedFromService = Object.assign(
          {
            phenomenonTime: currentDate.format(DATE_TIME_FORMAT),
            result: 'BBBBBB',
            resultTime: currentDate.format(DATE_TIME_FORMAT),
            property: 'BBBBBB',
            unitOfMeasurement: 'BBBBBB',
            intervalTime: 1,
            reserve: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            phenomenonTime: currentDate,
            resultTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Observation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
