import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../app.module';

import { SectionComponent } from './section.component';
import { SectionRoutes } from './section.routing';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { SectionService } from './section.service';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(SectionRoutes),
        FormsModule,
        MaterialModule,
        ChartsModule
    ],
    declarations: [
    	SectionComponent
    ],
    providers: [
    	SectionService
    ]
})

export class Section {}
