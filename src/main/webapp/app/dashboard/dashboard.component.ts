import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { Router } from '@angular/router';
import { DashboardService } from './dashboard.service';
import { Observable } from 'rxjs';
import { Layout } from './layout';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  // Declare variables
  data: Layout;
  adjustedScreenWidth: number;
  adjustedScreenHeight: number;
  layoutWidthMargin: number;
  layoutHeightMargin: number;
  oneWidthfeet: number; 
  oneHeightFeet: number; 
  headerHeight: number; 
  footerHeight: number;

  layout$: Observable<Layout>;
  

  constructor(
    private http: Http,
    private router: Router, 
    private dashboardService : DashboardService
  ) {}

  ngOnInit() {
    // Configurable value - to adjust Margin Height; used in top & bottom margins
    this.layoutHeightMargin = 25; 

    //Ensure that you get data from backend before preparing data for layout
    this.layout$ = this.dashboardService.retrieveOverallLayout(1);

    this.layout$.subscribe(
      res => {
        this.data = res;
        this.prepareLayoutData(window.innerWidth, window.innerHeight);
      },
      console.error
    );    
  }

  onResize() {
    // Whenever screen changes - redraw the layout!!!
    this.prepareLayoutData(window.innerWidth, window.innerHeight);
  }

  prepareLayoutData(screenWidth, screenHeight) {
    //Get header & Footer height, in order arrive at actual height available for layout
    this.headerHeight = document.getElementById('header').offsetHeight;
    // this.footerHeight = document.getElementById('footer').offsetHeight;
    // console.log(" Header Height : " + this.headerHeight);
    // console.log(" Footer Height : " + this.footerHeight);
    // console.log(" Total Width : " + screenWidth);
    // console.log(" Total Height : " + screenHeight);

    // Calculate adjusted screen height & width
    this.adjustedScreenHeight = screenHeight - (this.headerHeight + this.footerHeight + (2 * this.layoutHeightMargin));
    this.adjustedScreenWidth = this.getAdjustedScreenResolutionWidth(screenWidth, this.adjustedScreenHeight);
    // console.log(" Screen Size : " + this.adjustedScreenWidth + " : " + this.adjustedScreenHeight);

    if (this.data !== undefined) {
      // Arrive at logical One width Feet & One Height Feet
      this.oneWidthfeet = (this.adjustedScreenWidth / this.data.widthX);
      this.oneHeightFeet = (this.adjustedScreenHeight / this.data.heightY);
      // console.log(" One feet Width : " + this.oneWidthfeet);
      // console.log(" One feet Height : " + this.oneHeightFeet); 

      // Position the layout in the middle of the screen
      this.layoutWidthMargin = (screenWidth - this.adjustedScreenWidth)/2;
      // console.log(" Layout Margin Width : " + this.layoutWidthMargin);
    }

  }

  getAdjustedScreenResolutionWidth(screenWidth, screenHeight) {
    //Aspect ratio based adjustment to get screen width
    if ( screenHeight * (this.data.widthX/this.data.heightY) > this.data.widthX ) 
      return screenWidth * (this.data.heightY/this.data.widthX);
    else
      return screenHeight * (this.data.widthX/this.data.heightY);
  }

  calcMarginLeft(index) {
    // Calculate Left Margin of current section in comparision with co-ordinates from previous section
    if (index !== 0) { //Except first section
      if (this.data.sections[index].startY === this.data.sections[index - 1].startY) { //Sections in same row
        return this.data.sections[index].startX - this.data.sections[index - 1].endX;
      } 
    } 
    return this.data.sections[index].startX;
  }

  calcMarginTop(index) {
    // Calculate Top Margin of current section in comparision with co-ordinates from previous section
    if (index !== 0) { //Except first section
      if (this.data.sections[index].startY === this.data.sections[index - 1].startY) { //Sections in same row
        return this.data.sections[index].startY;
      } else { // Sections in different row
        return this.data.sections[index].startY - this.data.sections[index - 1].endY;
      }
    } else { // First Section
      return this.data.sections[index].startY;
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

  navigateToSection(id) {
    this.router.navigate(['/section/'+id])
  }

}