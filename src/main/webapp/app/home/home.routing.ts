import { Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';

export const HomeRoutes: Routes = [

    {
        path: '',
        children: [ {
            path: 'login',
            component: LoginComponent
          }
        ]
    }
];
