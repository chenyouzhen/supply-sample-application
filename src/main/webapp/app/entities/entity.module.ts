import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'factory',
        loadChildren: () => import('./factory/factory.module').then(m => m.SupplySampleApplicationFactoryModule)
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.SupplySampleApplicationProductModule)
      },
      {
        path: 'assembly-line',
        loadChildren: () => import('./assembly-line/assembly-line.module').then(m => m.SupplySampleApplicationAssemblyLineModule)
      },
      {
        path: 'observation',
        loadChildren: () => import('./observation/observation.module').then(m => m.SupplySampleApplicationObservationModule)
      },
      {
        path: 'alarm',
        loadChildren: () => import('./alarm/alarm.module').then(m => m.SupplySampleApplicationAlarmModule)
      },
      {
        path: 'kpi',
        loadChildren: () => import('./kpi/kpi.module').then(m => m.SupplySampleApplicationKpiModule)
      },
      {
        path: 'record',
        loadChildren: () => import('./record/record.module').then(m => m.SupplySampleApplicationRecordModule)
      },
      {
        path: 'link',
        loadChildren: () => import('./link/link.module').then(m => m.SupplySampleApplicationLinkModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SupplySampleApplicationEntityModule {}
