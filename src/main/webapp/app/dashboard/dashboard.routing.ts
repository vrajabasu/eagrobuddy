import { Routes } from '@angular/router';

import { DashboardComponent } from './dashboard.component';
import { SectionComponent } from './../section/section.component';

export const DashboardRoutes: Routes = [
    {

      path: '',
      children: [ {
        path: 'dashboard',
        component: DashboardComponent
    }
    ,{
        path: 'section/:sectionId',
        component: SectionComponent
    }
  ]
}
];
