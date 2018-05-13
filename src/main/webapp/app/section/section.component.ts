import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';

@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.css']
})
export class SectionComponent implements OnInit {
  sectionId: number;
  headerHeight;footerHeight;
  segCont; greenOptCont; historical: any;

  constructor(private activatedRoute: ActivatedRoute, private http: Http) { }

  ngOnInit() {
    this.headerHeight = document.getElementById('header').offsetHeight;
    this.footerHeight = document.getElementById('footer').offsetHeight;
    this.activatedRoute.params.subscribe(params => {
      this.sectionId = params['sectionId'];
      console.log(this.sectionId);
    }
    );
    this.segmentCondition();
    this.greensOptimal();
    this.historicalKPI();
  }

  segmentCondition() {
    return this.http.get('../assets/json/Wireframe2_c_Segment_Current_Condition_Top_Right_Corner.json').subscribe(
      res => {        
        this.segCont = res.json();
        console.log(this.segCont);
      },
      err => {
        console.log("Error occured.")
      }
    );
  }

  greensOptimal() {
    return this.http.get('../assets/json/Wireframe2_b_Section_Optimal_Condition_Bottom_Right_Corner.json').subscribe(
      res => {        
        this.greenOptCont = res.json();
        console.log(this.greenOptCont);
      },
      err => {
        console.log("Error occured.")
      }
    );
  }

  historicalKPI() {
    return this.http.get('../assets/json/Wireframe2_f_Historical_KPI_From_Sensor_Bottom_Left_Corner.json').subscribe(
      res => {        
        this.historical = res.json();
        console.log(this.historical);
      },
      err => {
        console.log("Error occured.")
      }
    );
  }
}
