import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {SharedModule} from '../shared/shared-module'

import { HomeRoutes } from './home.routing';
import { LoginComponent } from './login/login.component';
import { LoginService } from './login/loginService';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(HomeRoutes),
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  declarations: [
    LoginComponent
  ],
  providers: [
    LoginService
  ]
})

export class HomeModule {}
