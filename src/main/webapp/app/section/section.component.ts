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
  headerHeight; footerHeight;
  segCont; greenOptCont; historical; diffZone; overCondition: any;
  oneWidthfeet: number;
  oneHeightFeet: number;
  adjustedScreenWidth: number;
  adjustedScreenHeight: number;
  layoutWidthMargin: number;
  layoutHeightMargin: number;
  layoutWidth: number;
  layoutHeight: number;

  constructor(private activatedRoute: ActivatedRoute, private http: Http) { }

  ngOnInit() {
    this.layoutHeightMargin = 25;
    this.headerHeight = document.getElementById('header').offsetHeight;
    this.footerHeight = document.getElementById('footer').offsetHeight;
    this.layoutWidth = window.innerWidth;
    this.layoutHeight = window.innerHeight;
    console.log('layoutheight'+ this.layoutHeight);
    this.activatedRoute.params.subscribe(params => {
      this.sectionId = params['sectionId'];
      console.log(this.sectionId);
    }
    );
    this.segmentCondition();
    this.greensOptimal();
    this.historicalKPI();
    this.diffZones();
    this.overallCondition();
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

  diffZones() {
    return this.http.get('../assets/json/Wireframe2_e_Diff_Zones_with_Sensor_Middle_Compartment.json').subscribe(
      res => {
        this.diffZone = res.json();
        console.log(this.diffZone);
      },
      err => {
        console.log("Error occured.")
      }
    );
  }

  overallCondition() {
    return this.http.get('../assets/json/Wireframe2_a_Section_Overall_Condition_Top_Left_Corner.json').subscribe(
      res => {
        this.overCondition = res.json();
        console.log(this.overCondition);
        this.prepareLayoutData(window.innerWidth, window.innerHeight);
      },
      err => {
        console.log("Error occured.")
      }
    );
  }

  prepareLayoutData(screenWidth, screenHeight) {

    this.adjustedScreenHeight = screenHeight - (this.headerHeight + this.footerHeight + (2 * this.layoutHeightMargin));
    this.adjustedScreenWidth = this.getAdjustedScreenResolutionWidth(screenWidth, this.adjustedScreenHeight);
    console.log(this.adjustedScreenHeight);
    console.log(this.adjustedScreenWidth);

    if (this.overCondition !== undefined) {

      this.oneWidthfeet = (this.adjustedScreenWidth / this.overCondition.startX);
      this.oneHeightFeet = (this.adjustedScreenHeight / this.overCondition.startY);
      this.layoutWidthMargin = (screenWidth - this.adjustedScreenWidth) / 2;
      console.log(this.oneWidthfeet);
      console.log(this.oneHeightFeet);
      console.log(this.layoutWidthMargin);
    }

  }

  getAdjustedScreenResolutionWidth(screenWidth, screenHeight) {
    //Aspect ratio based adjustment to get screen width
    if (screenHeight * (this.overCondition.startX / this.overCondition.startY) > this.overCondition.startX)
      return screenWidth * (this.overCondition.startY / this.overCondition.startX);
    else
      return screenHeight * (this.overCondition.startX / this.overCondition.startY);
  }

}
