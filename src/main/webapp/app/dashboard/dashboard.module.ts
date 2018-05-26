import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../app.module';

import { DashboardComponent } from './dashboard.component';
import { DashboardRoutes } from './dashboard.routing';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { SectionComponent } from '../section/section.component';
import { DashboardService } from './dashboard.service';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(DashboardRoutes),
        FormsModule,
        MaterialModule,
        ChartsModule
    ],
    declarations: [
    	DashboardComponent,
    	SectionComponent
    ],
    providers: [
    	DashboardService
    ]
})

export class Dashboard {}
