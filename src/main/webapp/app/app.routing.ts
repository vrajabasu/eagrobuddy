import { Routes } from '@angular/router';
import { AuthGuard } from './shared/AuthGuard/auth-guard';
import { GHLayoutComponent } from './layouts/gh/gh-layout.component';
import { AuthLayoutComponent } from './layouts/auth/auth-layout.component';
import { RoleGuard } from './../app/shared/AuthGuard/role-guard';

export const GHRoutes: Routes = [
  {
    // Redirect app launch to login
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }, {
    /** PUBLIC ROUTES declared here **/
    //  Access open to public - evryone is allowed to see
    //  Prompts user to sign in to access the secure area */
    path: '',
    component: AuthLayoutComponent,
    //canActivate: [AuthGuard],
    children: [
      {
        path: '',
        loadChildren: './home/home.module#HomeModule'
      }
    ]
  },
  {
    /** SECURE ROUTES declared here **/
    // eAgro Buddy Module secure content area
    //  Access only for logged in User */
    path: '',
    component: GHLayoutComponent,
    // canActivateChild: [AuthGuard],
    children: [
      {
        path: '',
        loadChildren: './ghmgmt/ghmgmt.module#GHMgmt'
      },
      {
        path: '**',
        redirectTo: '/dashboard'
      }
      

    ]
  }
];
