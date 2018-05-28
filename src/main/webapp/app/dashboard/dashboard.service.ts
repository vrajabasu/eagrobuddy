import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs';
import { Layout } from './layout';

@Injectable()
export class DashboardService {

	constructor(private http:Http) { }

	//Retrieve Overall Layout Information
    retrieveOverallLayout(layoutId: number) {
	    let cpHeaders = new Headers({ 'Content-Type': 'application/json', "x-auth-token":localStorage.getItem('jwt') });
        let options = new RequestOptions({ headers: cpHeaders });
        return this.http.get('../assets/json/Wireframe1_Layout_1_Visualization_Complete.json', options)
               .map(this.extractData)
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