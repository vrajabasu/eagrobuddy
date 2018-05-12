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
  data: Layout;
  totalWidthPercentage: number;
  totalHeightPercentage: number;
  adjustedScreenWidth: number;
  adjustedScreenHeight: number;
  layoutWidthMargin: number;
  layoutHeightMargin: number;
  widthX; heightY; oneWidthfeet; oneHeightFeet; headerHeight; footerHeight;

  layout$: Observable<Layout>;
  

  constructor(
    private http: Http,
    private router: Router, 
    private dashboardService : DashboardService
  ) {}

  ngOnInit() {
    this.layoutHeightMargin = 25; /* This is constant configurable value - to adjust Margin Height */

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
    this.prepareLayoutData(window.innerWidth, window.innerHeight);
  }

  prepareLayoutData(screenWidth, screenHeight) {
    this.headerHeight = document.getElementById('header').offsetHeight;
    this.footerHeight = document.getElementById('footer').offsetHeight;
    console.log(" Header Height : " + this.headerHeight);
    console.log(" Footer Height : " + this.footerHeight);
    console.log(" Total Width : " + screenWidth);
    console.log(" Total Height : " + screenHeight);

    this.adjustedScreenHeight = screenHeight - (this.headerHeight + this.footerHeight + (2 * this.layoutHeightMargin));
    this.adjustedScreenWidth = this.getAdjustedScreenResolutionWidth(screenWidth, this.adjustedScreenHeight);
    console.log(" Adjusted Width : " + this.adjustedScreenWidth);
    console.log(" Adjusted Height : " + this.adjustedScreenHeight);

    if (this.data !== undefined) {
      console.log(" Screen Size : " + this.adjustedScreenWidth + " : " + this.adjustedScreenHeight);
      this.oneWidthfeet = (this.adjustedScreenWidth / this.data.widthX);
      this.oneHeightFeet = (this.adjustedScreenHeight / this.data.heightY);
      console.log(" One feet Width : " + this.oneWidthfeet);
      console.log(" One feet Height : " + this.oneHeightFeet); 
      this.layoutWidthMargin = (screenWidth - this.adjustedScreenWidth)/2;
      console.log(" Layout Margin Width : " + this.layoutWidthMargin);
    }

  }

  getAdjustedScreenResolutionWidth(screenWidth, screenHeight) {
    if ( screenHeight * (this.data.widthX/this.data.heightY) > this.data.widthX ) 
      return screenWidth * (this.data.heightY/this.data.widthX);
    else
      return screenHeight * (this.data.widthX/this.data.heightY);
  }

  calcMarginLeft(index) {
    if (index !== 0) {
      if (this.data.sections[index].startY === this.data.sections[index - 1].startY) {
        return this.data.sections[index].startX - this.data.sections[index - 1].endX;
      } 
    } 
    return this.data.sections[index].startX;
  }

  calcMarginTop(index) {
    if (index !== 0) {
      if (this.data.sections[index].startY === this.data.sections[index - 1].startY) {
        return this.data.sections[index].startY;
      } else {
        return this.data.sections[index].startY - this.data.sections[index - 1].endY;
      }
    } else {
      return this.data.sections[index].startY;
    }
  }

  assignBgClr(clrvalue) {
    if (clrvalue === 'EXCEEDED') {
      return "red";
    } else if (clrvalue === 'EXCEEDING_SOON') {
      return 'yellow'
    } else {
      return 'green'
    }
  }

  goToPage(id) {
    console.log(id);
    this.router.navigate(['/section/'+id])
  }

}