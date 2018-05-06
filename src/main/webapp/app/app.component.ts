import { Component, OnInit } from '@angular/core';

declare var $: any;

@Component({
    selector: 'app-main',
    templateUrl: './app.component.html'
})

export class GHComponent implements OnInit {

    constructor() {}

    ngOnInit() {
        $.material.init();
    }
}
