import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AssemblyLineService } from 'app/entities/assembly-line/assembly-line.service';
import { IAssemblyLine, AssemblyLine } from 'app/shared/model/assembly-line.model';

describe('Service Tests', () => {
  describe('AssemblyLine Service', () => {
    let injector: TestBed;
    let service: AssemblyLineService;
    let httpMock: HttpTestingController;
    let elemDefault: IAssemblyLine;
    let expectedResult: IAssemblyLine | IAssemblyLine[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AssemblyLineService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AssemblyLine(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            capacity: currentDate.format(DATE_TIME_FORMAT),
            planCapacity: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AssemblyLine', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            capacity: currentDate.format(DATE_TIME_FORMAT),
            planCapacity: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            capacity: currentDate,
            planCapacity: currentDate
          },
          returnedFromService
        );

        service.create(new AssemblyLine()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AssemblyLine', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            type: 'BBBBBB',
            description: 'BBBBBB',
            capacity: currentDate.format(DATE_TIME_FORMAT),
            planCapacity: currentDate.format(DATE_TIME_FORMAT),
            reserve: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            capacity: currentDate,
            planCapacity: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AssemblyLine', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            type: 'BBBBBB',
            description: 'BBBBBB',
            capacity: currentDate.format(DATE_TIME_FORMAT),
            planCapacity: currentDate.format(DATE_TIME_FORMAT),
            reserve: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            capacity: currentDate,
            planCapacity: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AssemblyLine', () => {
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
