import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { CCMModule } from './app.module';
import {enableProdMode} from '@angular/core';

enableProdMode();
platformBrowserDynamic().bootstrapModule(CCMModule);
