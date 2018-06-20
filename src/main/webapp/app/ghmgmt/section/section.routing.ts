import { Routes } from '@angular/router';

import { SectionComponent } from './section.component';

export const SectionRoutes: Routes = [
    {
      path: '',
      redirectTo: 'section',  
      pathMatch: 'full'
    }, {
      path: '',
      children: [ {
        path: 'section',
        component: SectionComponent
    }]}
];
