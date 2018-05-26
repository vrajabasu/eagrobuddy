import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs';
import { Layout } from '../dashboard/layout';

@Injectable()
export class SectionService {

	constructor(private http:Http) { }

	//Retrieve Segment Current Information
    segmentCurrentCondition() {
	    let cpHeaders = new Headers({ 'Content-Type': 'application/json', "x-auth-token":localStorage.getItem('jwt') });
        let options = new RequestOptions({ headers: cpHeaders });
        return this.http.get('../assets/json/Wireframe2_c_Segment_Current_Condition_Top_Right_Corner.json', options)
               .map(this.extractData)
               .catch(this.handleError);
    }

    //Retrieve Segment Optimal Information
    segmentOptimalCondition() {
        let cpHeaders = new Headers({ 'Content-Type': 'application/json', "x-auth-token":localStorage.getItem('jwt') });
        let options = new RequestOptions({ headers: cpHeaders });
        return this.http.get('../assets/json/Wireframe2_b_Section_Optimal_Condition_Bottom_Right_Corner.json', options)
               .map(this.extractData)
               .catch(this.handleError);
    }

    //Retrieve Segment KPI Chart Values
    segmentKPIChart() {
        let cpHeaders = new Headers({ 'Content-Type': 'application/json', "x-auth-token":localStorage.getItem('jwt') });
        let options = new RequestOptions({ headers: cpHeaders });
        return this.http.get('../assets/json/Wireframe2_f_Historical_KPI_From_Sensor_Bottom_Left_Corner.json', options)
               .map(this.extractData)
               .catch(this.handleError);
    } 

    //Retrieve Segment Zone Information
    segmentZoneCondition() {
        let cpHeaders = new Headers({ 'Content-Type': 'application/json', "x-auth-token":localStorage.getItem('jwt') });
        let options = new RequestOptions({ headers: cpHeaders });
        return this.http.get('../assets/json/Wireframe2_e_Diff_Zones_with_Sensor_Middle_Compartment.json', options)
               .map(this.extractData)
               .catch(this.handleError);
    }     

    //Retrieve Segment Overall Information
    segmentOverallCondition() {
        let cpHeaders = new Headers({ 'Content-Type': 'application/json', "x-auth-token":localStorage.getItem('jwt') });
        let options = new RequestOptions({ headers: cpHeaders });
        return this.http.get('../assets/json/Wireframe2_a_Section_Overall_Condition_Top_Left_Corner.json', options)
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