import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../app.module';

import { Dashboard } from './dashboard/dashboard.module';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { GHMgmtRoutes } from './ghmgmt.routing';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(GHMgmtRoutes),
        FormsModule,
        MaterialModule,
        ChartsModule,
        Dashboard
    ],
    declarations: [
    ],
    providers: [
    ]
})

export class GHMgmt {}
