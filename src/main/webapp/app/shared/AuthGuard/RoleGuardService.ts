import { Injectable } from "@angular/core";
import {
  Http,
  Response,
  Headers,
  URLSearchParams,
  RequestOptions
} from "@angular/http";
import { Observable } from "rxjs";

@Injectable()
export class RoleGuardService {
  //URLs for CRUD operations
  roleCheckUrl = "http://localhost:8080/api/rest/isAdmin";
  //resetUrl = "http://localhost:8080/api/rest/reset";
  //Create constructor to get Http instance
  constructor(private http: Http) {}

  roleCheck(): Observable<number> {

    let cpHeaders = new Headers({ 'Content-Type': 'application/json', "x-auth-token":localStorage.getItem('jwt') });
    let options = new RequestOptions({ headers: cpHeaders });
    return this.http.get(this.roleCheckUrl,options)
    .map(success => success.status)
    .catch(this.handleError);
}

private extractData(res: Response) {
    let body = res.json();
    return body;
}
private handleError (error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
}

  }