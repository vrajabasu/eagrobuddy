import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {Response} from "@angular/http";
import {Subject} from "rxjs/Rx";
import { Http } from '@angular/http';

@Injectable()
export class LoginService{

  constructor(private http:Http) {}

  login(userName, password):Observable<Response> {
    let loginRequest = JSON.stringify({username: userName, password: password});
    let headers = new Headers({'Content-Type': 'application/json', 'Accept': 'application/json'});
    headers.append('Access-Control-Allow-Headers', 'Content-Type');


    return this.http.post('http://localhost:8080/api/rest/eAgro/v1/users/login', loginRequest)
                    .do(resp => {
                        localStorage.setItem('jwt', resp.headers.get('x-auth-token'));
                    }).catch(this.handleError);
}
    
private handleError(error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
  }


     isSignedIn():boolean {
        return localStorage.getItem('jwt') !== null;
    }


}
