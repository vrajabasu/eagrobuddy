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
      return "#ff3333"; //#ff765e"; // Light RED
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return '#ffff00' // Light Yellow
    } else {
      return '#00ff00' // Light Green
    }
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
      return "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAYUlEQVQoU2P85e39nwEJhNTcYljTooYsBGYzIisEKYIBdMWkK0Q2DZupcBOJUohNEbqpYBORFZpPesFwMk8Cw1OMfsdVUYIHXSFIBygEUHwNEuzmO8RQ+skOfzhiyCIJAAA6zDihIDPubAAAAABJRU5ErkJggg==) repeat"; // Light Red
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAYUlEQVQoU2P89EvoPwMSiDkrzLDE+C2yEJjNiKwQpAgG0BWTrhDZNGymwk0kSiE2Reimgk1EVmg+6QXDyTwJDE8x+h1XRQkedIUgHaAQQPE1SHBy/G+G3IWs+MMRQxZJAAC5xTztYN1JbwAAAABJRU5ErkJggg==) repeat"; // Light Yellow
    } else {
      return "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAYklEQVQoU2MUzlP9z4AErCMZGI4uRxaBsBmRFYIUwQC6YtIVIpuGzVS4iUQpxKYI3VSwicgKzSe9YDiZJ4HhKUa/46jBg64QpAMUAii+BgkWvX7B0CeKMBFmNIZCzKCGiAAAI1spfbSN7hEAAAAASUVORK5CYII=) repeat"; // Light Green
    } 
  }  

  // Dot Pattern
  assignBgClr3(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAASklEQVQoU42QUQ4AIAhC4f6HtumykbllX5ZPSGiAESD28buX9e0A3tShKnApqUodDLAqqXX22EH533QIZgyOrTWW7zId/MQzDXwB0fg0A/d01CAAAAAASUVORK5CYII=) repeat"; // Light Red
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAS0lEQVQoU2P89UvjPxvbDUYGKADxQUx0MbgCkCSyJnQDUExCNgVdI1ghuknIVsPkGLEpgrkXZgNIDfEKibYaOVgIegabYozgITbAAZkDZHfNqOryAAAAAElFTkSuQmCC) repeat"; // Light Yellow
    } else {
      return "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAS0lEQVQoU2N0+aXxfw/bDUYGKADxQUx0MbgCkCSyJnQDUExCNgVdI1ghuknIVsPkGLEpgrkXZgNIDfEKibYaOVgIegabYozgITbAASXZYZ/hJtSoAAAAAElFTkSuQmCC) repeat"; // Light Green
    } 
  }    


  // Hero Pattern - leaf
  assignBgClr4(clrvalue) {
    // Set back ground color based on overall threshold status
    if (clrvalue === 'EXCEEDED') {
      return "url("+"data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 80 40' width='80' height='40'%3E%3Cpath fill='%232069bb' fill-opacity='0.4' d='M0 40a19.96 19.96 0 0 1 5.9-14.11 20.17 20.17 0 0 1 19.44-5.2A20 20 0 0 1 20.2 40H0zM65.32.75A20.02 20.02 0 0 1 40.8 25.26 20.02 20.02 0 0 1 65.32.76zM.07 0h20.1l-.08.07A20.02 20.02 0 0 1 .75 5.25 20.08 20.08 0 0 1 .07 0zm1.94 40h2.53l4.26-4.24v-9.78A17.96 17.96 0 0 0 2 40zm5.38 0h9.8a17.98 17.98 0 0 0 6.67-16.42L7.4 40zm3.43-15.42v9.17l11.62-11.59c-3.97-.5-8.08.3-11.62 2.42zm32.86-.78A18 18 0 0 0 63.85 3.63L43.68 23.8zm7.2-19.17v9.15L62.43 2.22c-3.96-.5-8.05.3-11.57 2.4zm-3.49 2.72c-4.1 4.1-5.81 9.69-5.13 15.03l6.61-6.6V6.02c-.51.41-1 .85-1.48 1.33zM17.18 0H7.42L3.64 3.78A18 18 0 0 0 17.18 0zM2.08 0c-.01.8.04 1.58.14 2.37L4.59 0H2.07z'%3E%3C/path%3E%3C/svg%3E"+")"; // Light Red
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAS0lEQVQoU2P89UvjPxvbDUYGKADxQUx0MbgCkCSyJnQDUExCNgVdI1ghuknIVsPkGLEpgrkXZgNIDfEKibYaOVgIegabYozgITbAAZkDZHfNqOryAAAAAElFTkSuQmCC) repeat"; // Light Yellow
    } else {
      return "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAS0lEQVQoU2N0+aXxfw/bDUYGKADxQUx0MbgCkCSyJnQDUExCNgVdI1ghuknIVsPkGLEpgrkXZgNIDfEKibYaOVgIegabYozgITbAASXZYZ/hJtSoAAAAAElFTkSuQmCC) repeat"; // Light Green
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