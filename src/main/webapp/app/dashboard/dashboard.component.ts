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
  noOfRows: number;

  layout$: Observable<Layout>;
  

  constructor(
    private http: Http,
    private router: Router, 
    private dashboardService : DashboardService
  ) {}

  
  ngOnInit() {
    // Configurable value - to adjust Margin Height; used in top & bottom margins
    this.layoutHeightMargin = 5; 

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
    console.log(" Header Height : " + this.headerHeight);
    console.log(" Total Width : " + screenWidth);
    console.log(" Total Height : " + screenHeight);

    // Calculate adjusted screen height & width
    this.adjustedScreenHeight = screenHeight - (this.headerHeight + (2 * this.layoutHeightMargin));
    this.adjustedScreenWidth = this.adjustedScreenHeight * (this.data.widthX / this.data.heightY);
    if (this.adjustedScreenWidth > screenWidth) {
      this.adjustedScreenWidth = screenWidth;
      this.adjustedScreenHeight = screenWidth * (this.data.heightY / this.data.widthX);
    }
    console.log(" Adjusted Screen Size : " + this.adjustedScreenWidth + " : " + this.adjustedScreenHeight);

    if (this.data !== undefined) {
      // Arrive at logical One width Feet & One Height Feet
      this.oneWidthfeet = (this.adjustedScreenWidth / this.data.widthX);
      this.oneHeightFeet = (this.adjustedScreenHeight / this.data.heightY);
      console.log(" One feet Width : " + this.oneWidthfeet);
      console.log(" One feet Height : " + this.oneHeightFeet); 

      // Position the layout in the middle of the screen
      this.layoutWidthMargin = (screenWidth - this.adjustedScreenWidth)/2;
      if (this.adjustedScreenHeight < (screenHeight - (this.headerHeight + (2 * this.layoutHeightMargin)))) {
        this.layoutHeightMargin += (((screenHeight - (this.headerHeight + (2 * this.layoutHeightMargin))) - this.adjustedScreenHeight)/2);
      }
      if (screenWidth === this.adjustedScreenWidth) {
        this.layoutWidthMargin = 5; //Set the margin when screen width is not changed!       
      }
      console.log(" Layout Margin Width : " + this.layoutWidthMargin);
      console.log(" Layout Margin Height : " + this.layoutHeightMargin);
    }

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
      return "radial-gradient(white 25%, red 75%)"; //#ff765e"; // Light RED
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "radial-gradient(white 5%, yellow 75%)"; // Light Yellow
    } else {
      return "radial-gradient(white 5%, green 75%)"; // Light Green
    }
  }

    assignBgImg(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "#ff765e url('../../assets/img/topography.png')"; // Light RED
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "#fff249 url('../../assets/img/round.png')"; // Light Yellow
    } else {
      return "#42f480 url('../../assets/img/spiration-light.png')"; // Light Green
    }

    // return "url('../../assets/img/sun-pattern.png')"; 
  }

  assignBgImgGradient(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "linear-gradient(#e60000, #ff9999), url('../../assets/img/topography.png')"; // Light RED
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "linear-gradient(#ffff00, #ffffb3), url('../../assets/img/round.png')"; // Light Yellow
    } else {
      return "linear-gradient(#003300, #99ff99), url('../../assets/img/spiration-light.png')"; // Light Green
    }

    // return "url('../../assets/img/sun-pattern.png')"; 
  }

    assignBoxShadow(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "inset 0 0 0 2000px rgba(255,0,0,0.7)"; // Light RED
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "inset 0 0 0 2000px rgba(255,255,0,0.5)"; // Light Yellow
    } else {
      return "inset 0 0 0 2000px rgba(0, 153, 51,0.5)"; // Light Green
    }

    // return "url('../../assets/img/sun-pattern.png')"; 
  }  

  assignBgClr1(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "url('../../assets/img/red.gif')"; // Light RED
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "url('../../assets/img/yellow.gif')"; // Light Yellow
    } else {
      return "url('../../assets/img/green.gif')"; // Light Green
    }
  }

  //Tree Pattern
  assignBgClr2(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "linear-gradient(#ffb3b3, #ff0000)"; // Light Red
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "linear-gradient(#ffffcc, #ffff00)"; // Light Yellow
    } else {
      return "linear-gradient(#ccffdd, #009900)"; // Light Green
    } 
  }  

  // Dot Pattern
  assignBgClr3(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "radial-gradient(rgb(93,0,0) 4%, rgb(92,0,0) 9%, rgba(75,0,0,0) 9%), radial-gradient(rgb(75,0,0) 4%, rgb(92,0,0) 8%, rgba(75,0,0,0) 10%), radial-gradient(rgba(153,0,0,0.8) 0, rgba(153,0,0,0.8) 21%, rgba(102,0,0,0) 100%), radial-gradient(rgba(153,0,0,0.8) 20%, rgba(102,0,0,0) 100%), radial-gradient(rgb(102,0,0) 35%, rgba(102,0,0,0) 60%), radial-gradient(rgb(102,0,0) 35%, rgba(102,0,0,0) 60%), radial-gradient(rgba(76,0,0,0.701961) 0, rgba(102,0,0,0) 100%), radial-gradient(rgba(76,0,0,0.701961) 0, rgba(102,0,0,0) 100%), linear-gradient(45deg, rgba(102,0,0,0) 49%, rgb(0,0,0) 50%, rgba(102,0,0,0) 70%), linear-gradient(-45deg, rgba(102,0,0,0) 49%, rgb(0,0,0) 50%, rgba(102,0,0,0) 70%), rgb(51, 0, 0)"; // Light Red
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "radial-gradient(rgba(238,242,4,1) 0, rgba(242,242,4,1) 4%, rgba(243,247,14,1) 8%, rgba(225,244,14,1) 9%, rgba(102,0,0,0) 10%, rgba(102,0,0,0) 100%), radial-gradient(rgba(244,233,26,1) 0, rgba(219,244,24,1) 4%, rgba(230,247,42,1) 8%, rgba(102,0,0,0) 10%, rgba(102,0,0,0) 100%), radial-gradient(rgba(233,247,32,0.8) 0, rgba(227,242,24,0.8) 21%, rgba(102,0,0,0) 100%), radial-gradient(rgba(221,242,60,0.8) 0, rgba(210,237,40,0.8) 20%, rgba(102,0,0,0) 100%), radial-gradient(rgba(230,237,52,1) 0, rgba(230,237,40,1) 35%, rgba(102,0,0,0) 60%, rgba(102,0,0,0) 100%), radial-gradient(rgba(229,244,66,1) 0, rgba(226,242,48,1) 35%, rgba(102,0,0,0) 60%, rgba(102,0,0,0) 100%), radial-gradient(rgba(235,242,53,0.7) 0, rgba(102,0,0,0) 100%), radial-gradient(rgba(235,242,41,0.7) 0, rgba(102,0,0,0) 100%), linear-gradient(45deg, rgba(102,0,0,0) 49%, rgb(0,0,0) 50%, rgba(102,0,0,0) 70%), linear-gradient(-45deg, rgba(102,0,0,0) 49%, rgb(0,0,0) 50%, rgba(102,0,0,0) 70%), rgba(247,231,61,1)"; // Light Yellow
    } else {
      return "radial-gradient(rgba(39,137,0,1) 0, rgba(9,137,0,1) 4%, rgba(39,137,0,1) 8%, rgba(21,91,0,1) 9%, rgba(102,0,0,0) 10%, rgba(102,0,0,0) 100%), radial-gradient(rgba(6,137,0,1) 0, rgba(2,137,0,1) 4%, rgba(0,91,1,1) 8%, rgba(102,0,0,0) 10%, rgba(102,0,0,0) 100%), radial-gradient(rgba(20,153,0,0.8) 0, rgba(10,153,0,0.8) 21%, rgba(102,0,0,0) 100%), radial-gradient(rgba(0,153,7,0.8) 0, rgba(33,153,0,0.8) 20%, rgba(102,0,0,0) 100%), radial-gradient(rgba(0,102,1,1) 0, rgba(0,102,0,1) 35%, rgba(102,0,0,0) 60%, rgba(102,0,0,0) 100%), radial-gradient(rgba(11,102,0,1) 0, rgba(1,102,0,1) 35%, rgba(102,0,0,0) 60%, rgba(102,0,0,0) 100%), radial-gradient(rgba(8,73,0,0.7) 0, rgba(102,0,0,0) 100%), radial-gradient(rgba(20,73,0,0.7) 0, rgba(102,0,0,0) 100%), linear-gradient(45deg, rgba(102,0,0,0) 49%, rgb(0,0,0) 50%, rgba(102,0,0,0) 70%), linear-gradient(-45deg, rgba(102,0,0,0) 49%, rgb(0,0,0) 50%, rgba(102,0,0,0) 70%), rgba(17,51,0,1)"; // Light Green
    } 
  }    

  // Dot Pattern
  assignBgClr4(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "linear-gradient(135deg, rgba(248,80,50,1) 0%, rgba(241,111,92,1) 35%, rgba(240,47,23,1) 54%, rgba(231,56,39,1) 100%)"; // Light Red
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "linear-gradient(135deg, rgba(239,245,125,1) 0%, rgba(233,242,102,1) 40%, rgba(255,255,0,1) 50%, rgba(255,255,0,1) 71%, rgba(255,255,0,1) 100%)"; // Light Yellow
    } else {
      return " linear-gradient(135deg, rgba(163,245,142,1) 0%, rgba(145,242,106,1) 33%, rgba(59,191,32,1) 51%, rgba(69,207,15,1) 71%, rgba(78,156,39,1) 100%)"; // Light Green
    } 
  }    


  // Hero Pattern - leaf
  assignBgClr5(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "linear-gradient(#e60000, #ff9999)"; // Light Red
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "linear-gradient(#ffff00, #ffffb3)"; // Light Yellow
    } else {
      return "linear-gradient(#003300, #99ff99)"; // Light Green
    } 
  }  

  calcSectionLabelLength( index) {
    // 1 character (X) = 8.00000000000007 pixel (X)
    return this.data.sections[index].sectionName.length * 8.00000000000007;
  }

  navigateToSection(id) {
    this.router.navigate(['/section/'+id])
  }

}