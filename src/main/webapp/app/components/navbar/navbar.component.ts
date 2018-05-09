import { Component, OnInit, Renderer, ViewChild, ElementRef, Directive } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd ,RoutesRecognized,NavigationStart} from '@angular/router';
import { Location, LocationStrategy, PathLocationStrategy } from '@angular/common';
const misc: any = {
    navbar_menu_visible: 0,
    active_collapse: true,
    disabled_collapse_init: 0,
};

declare var $: any;
@Component({
    selector: 'app-navbar',
    templateUrl: 'navbar.component.html',
    styleUrls: ['navbar.component.css']
})

export class NavbarComponent implements OnInit {
    location: Location;
    private nativeElement: Node;
    private toggleButton: any;
    private sidebarVisible: boolean;

    private pathArray:any[];

    layouts = ['Layout1', 'Layout2', 'Layout3'];
  

    @ViewChild('app-navbar-cmp') button: any;

    constructor(location: Location, 
                private renderer: Renderer,
                private element: ElementRef,
                private route: ActivatedRoute,
                private router: Router,) {
        this.location = location;
        this.nativeElement = element.nativeElement;
    }

    ngOnInit() {
      
        const navbar: HTMLElement = this.element.nativeElement;
        this.toggleButton = navbar.getElementsByClassName('navbar-toggle')[0];

    }

    onResize(event) {
      if ($(window).width() > 991) {
        return false;
      }
      return true;
    }

    getPath() {
        return this.location.prepareExternalUrl(this.location.path());
    }

    logout()
    {
        localStorage.removeItem('jwt');
    }
}
