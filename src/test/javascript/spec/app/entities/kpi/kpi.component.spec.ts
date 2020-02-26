import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SupplySampleApplicationTestModule } from '../../../test.module';
import { KpiComponent } from 'app/entities/kpi/kpi.component';
import { KpiService } from 'app/entities/kpi/kpi.service';
import { Kpi } from 'app/shared/model/kpi.model';

describe('Component Tests', () => {
  describe('Kpi Management Component', () => {
    let comp: KpiComponent;
    let fixture: ComponentFixture<KpiComponent>;
    let service: KpiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SupplySampleApplicationTestModule],
        declarations: [KpiComponent]
      })
        .overrideTemplate(KpiComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KpiComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KpiService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Kpi(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.kpis && comp.kpis[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
