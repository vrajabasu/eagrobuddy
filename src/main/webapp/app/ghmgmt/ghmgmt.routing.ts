import { Routes } from '@angular/router';

export const GHMgmtRoutes: Routes = [
  {
    path: '',
    children: [ {
      path: 'dashboard',
      loadChildren: './dashboard/dashboard.module#Dashboard'
    } ]
  },
  {
    path: '',
    children: [ {
      path: 'sections',
      loadChildren: './section/section.module#Section'
    }]
  }
];
