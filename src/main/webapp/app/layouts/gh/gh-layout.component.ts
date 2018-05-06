import { Component, OnInit, OnDestroy, ViewChild, HostListener, AfterViewInit } from '@angular/core';
import { Router, NavigationEnd, NavigationStart } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Location, LocationStrategy, PathLocationStrategy, PopStateEvent } from '@angular/common';
import 'rxjs/add/operator/filter';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import PerfectScrollbar from 'perfect-scrollbar';

declare const $: any;

@Component({
  selector: 'app-main',
  templateUrl: './gh-layout.component.html'
})

export class GHLayoutComponent implements OnInit {
    constructor( private router: Router, location: Location ) {
    }
    ngOnInit() { }
}
