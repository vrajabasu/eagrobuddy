import { Injectable } from '@angular/core';
import { Router, CanActivateChild, ActivatedRouteSnapshot } from '@angular/router';
import {JwtHelper} from 'angular2-jwt';
import decode from 'jwt-decode';

@Injectable()
export class RoleGuard implements CanActivateChild {

    jwtHelper: JwtHelper = new JwtHelper();

  constructor(private router: Router) {}

  canActivateChild() {
    let token= localStorage.getItem('jwt');
   
   const url: string = this.router.url;
   const tokenPayload = decode(token);


   if (tokenPayload.Role === "[ROLE_USER]") {
         this.router.navigate(['/dashboard']);
        return false;
     }
       return true;
  }
}