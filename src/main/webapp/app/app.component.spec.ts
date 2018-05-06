import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, DebugElement, NO_ERRORS_SCHEMA } from '@angular/core';
import { By } from '@angular/platform-browser';

import { CCMComponent } from './app.component';

@Component({selector: 'router-outlet', template: ''})
class RouterOutletStubComponent { }

@Component({selector: 'ccm-main', template: ''})
class CCMMainComponent { }

let app:    CCMComponent;
let fixture: ComponentFixture<CCMComponent>;

describe('CCMComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CCMComponent,
        RouterOutletStubComponent,
        CCMMainComponent
      ],
    }).compileComponents()
       .then(() => {
	      fixture = TestBed.createComponent(CCMComponent);
	      app    = fixture.componentInstance;
    });

  }));

  it('should create the CCM app', async(() => {
    expect(app).toBeTruthy();
  }));

  it('can instantiate the component', () => {
    expect(app).not.toBeNull();
  });

});