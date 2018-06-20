import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Http, Response, Headers, URLSearchParams, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs';

import { SectionService } from './section.service';

@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.css']
})
export class SectionComponent implements OnInit {
  sectionId: number;
  headerHeight; footerHeight;
  segCont; greenOptCont; historical; diffZone; overallCondition: any;
  oneWidthfeet: number;
  oneHeightFeet: number;
  adjustedScreenWidth: number;
  adjustedScreenHeight: number;
  sectionMargin: number;
  layoutWidthMargin: number;
  layoutHeightMargin: number;
  layoutWidth: number;
  layoutHeight: number;
  segmentId: number;
  selectedIndex: number = null;
  sectionWidth: number;
  sectionHeight: number;

  sectionOverallCondition$: Observable<any>;
  segmentCurrentCondition$: Observable<any>;
  sectionOptimalCondition$: Observable<any>;
  sectionKPIChart$: Observable<any>;
  segmentZoneCondition$: Observable<any>;

  constructor(private activatedRoute: ActivatedRoute, private http: Http, private sectionService: SectionService) { }

  ngOnInit() {

    this.selectedIndex = 0;

    this.activatedRoute.queryParams.subscribe(params => {
      this.sectionId = +params['sectionId'];
      console.log("Selected Section : " + this.sectionId);
    }
    );

    // Configurable value - to adjust Margin Height; used in top & bottom margins
    this.layoutHeightMargin = 5;
    this.headerHeight = document.getElementById('header').offsetHeight;

    //Ensure that you get data from backend before preparing data for layout
    this.sectionOverallCondition$ = this.sectionService.sectionOverallCondition(this.sectionId);
    this.segmentCurrentCondition$ = this.sectionService.segmentCurrentCondition(this.segmentId);
    this.sectionOptimalCondition$ = this.sectionService.sectionOptimalCondition(this.sectionId);
    this.sectionKPIChart$ = this.sectionService.sectionKPIChart(this.sectionId);
    this.segmentZoneCondition$ = this.sectionService.segmentZoneCondition(this.segmentId);

    // Get the available height & width from window object
    this.layoutWidth = window.innerWidth;
    this.layoutHeight = window.innerHeight;
    console.log("Screen Resolution : " + this.layoutWidth + " , " + this.layoutHeight);

    this.sectionOverallCondition$.subscribe(
      res => {
        this.overallCondition = res;
        this.sectionWidth = this.overallCondition.endX - this.overallCondition.startX;
        this.sectionHeight = this.overallCondition.endY - this.overallCondition.startY;
        this.prepareSectionData(window.innerWidth, window.innerHeight);
        console.log("Section Overall Condition : " + this.overallCondition);
      },
      console.error
    );

    this.segmentCurrentCondition$.subscribe(
      res => {
        this.segCont = res;
        console.log("Segment Current Condition : " + this.segCont);
      },
      console.error
    );

    this.sectionOptimalCondition$.subscribe(
      res => {
        this.greenOptCont = res;
        console.log("Section Optimal Condition : " + this.greenOptCont);
      },
      console.error
    );

    this.sectionKPIChart$.subscribe(
      res => {
        this.historical = res;
        console.log("Section KPI Chart : ");
        console.log(this.historical);
      },
      console.error
    );

    this.segmentZoneCondition$.subscribe(
      res => {
        this.diffZone = res;
        console.log(this.diffZone);
      },
      console.error
    );

  }

  onResize() {
    // Whenever screen changes - redraw the layout!!!
    this.prepareSectionData(window.innerWidth, window.innerHeight);
  }

  prepareSectionData(screenWidth, screenHeight) {

    //Get header & Footer height, in order arrive at actual height available for layout
    this.headerHeight = document.getElementById('header').offsetHeight;

    //Configurable Margin to draw the section - all sides
    this.sectionMargin = 25;
    console.log("Section Margin : " + this.sectionMargin);

    this.adjustedScreenHeight = ((screenHeight - this.headerHeight) * 0.4) - (this.sectionMargin * 2);
    // this.adjustedScreenWidth = (screenWidth * 0.33) - (this.sectionMargin * 2);
    this.adjustedScreenWidth = this.adjustedScreenHeight * (this.sectionWidth/this.sectionHeight);
    if (this.adjustedScreenWidth > ((screenWidth * 0.33) - (this.sectionMargin * 2))) {
      this.adjustedScreenWidth = (screenWidth * 0.33) - (this.sectionMargin * 2);
      this.adjustedScreenHeight = ((screenWidth * 0.33) - (this.sectionMargin * 2)) * (this.sectionWidth/this.sectionHeight);
    }    
    console.log("Adjusted Screen Resolution : " + this.adjustedScreenWidth + " , " + this.adjustedScreenHeight);

    if (this.overallCondition !== undefined) {
      // this.oneWidthfeet = (this.adjustedScreenWidth / (this.overallCondition.endX - this.overallCondition.startX));
      // this.oneHeightFeet = (this.adjustedScreenHeight / (this.overallCondition.endY - this.overallCondition.startY));
      this.oneWidthfeet = (this.adjustedScreenWidth / this.sectionWidth);
      this.oneHeightFeet = (this.adjustedScreenHeight / this.sectionHeight);      
      console.log("One Feet Width & Height : " + this.oneWidthfeet + " , " + this.oneHeightFeet);
    }
  }

  assignBgClr(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "#ff765e"; // Light RED
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return '#fff249' // Light Yellow
    } else {
      return '#42f480' // Light Green
    }
  }

  setIndex(index: number) {
    this.selectedIndex = index;
  }

}
