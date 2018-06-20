import { Routes } from '@angular/router';

import { DashboardComponent } from './dashboard.component';
import { SectionComponent } from './../section/section.component';

export const DashboardRoutes: Routes = [
    {
      path: '',
      redirectTo: 'dashboard',  
      pathMatch: 'full'
    }, {
      path: '',
      children: [ {
        path: 'dashboard',
        component: DashboardComponent
    }]}
];
