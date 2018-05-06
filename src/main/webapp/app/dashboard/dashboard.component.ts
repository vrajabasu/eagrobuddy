import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  data: any;
  totalWidthPercentage: any;
  totalHeightPercentage: any;
  widthX; heightY; onefeet; oneHeightFeet; headerHeight; footerHeight;
  layouts = ['Layout1', 'Layout2', 'Layout3'];

  constructor(private http: Http,private router: Router) {}

  ngOnInit() {
    this.drawFormLayout(window.screen.width, window.screen.height);
  }
  onResize() {
    this.drawFormLayout(window.screen.width, window.screen.height);
  }
  drawFormLayout(x, y) {
    this.http.get('../assets/json/Wireframe1_Layout_Visualization.json')
      .subscribe(
        res => {
          this.data = res.json();
          this.widthX = this.data.widthX;
          this.heightY = this.data.heightY;
          console.log(this.data);
          // console.log(res.headers.get('Content-Type'));
          this.totalWidthPercentage = x;
          this.totalHeightPercentage = y;
          this.onefeet = (this.totalWidthPercentage / this.widthX);
          this.oneHeightFeet = (this.totalHeightPercentage / this.heightY);
          console.log(this.totalWidthPercentage);
          console.log(this.totalHeightPercentage);
          this.headerHeight = document.getElementById('header').offsetHeight;
          this.footerHeight = document.getElementById('footer').offsetHeight;
          console.log(this.headerHeight);
          console.log(this.footerHeight);
        },
        err => {
          console.log(err);
        }
      )
  }



  calcMarginLeft(index) {
    if (index != 0) {
      if (this.data.sections[index].startY == this.data.sections[index - 1].startY) {
        return this.data.sections[index].startX - this.data.sections[index - 1].endX;
      } else {
        return this.data.sections[index].startX;
      }
    } else {
      return this.data.sections[index].startX;
    }
  }

  calcMarginTop(index) {
    if (index != 0) {
      if (this.data.sections[index].startX == this.data.sections[index - 1].startX) {
        return this.data.sections[index].startY - this.data.sections[index - 1].endY;
      } else {
        return this.data.sections[index].startY;
      }
    } else {
      return this.data.sections[index].startY;
    }
  }

  assignBgClr(clrvalue) {
    if (clrvalue == 'EXCEEDED') {
      return "red";
    } else if (clrvalue == 'EXCEEDING_SOON') {
      return 'yellow'
    } else {
      return 'green'
    }
  }

  goToPage(id) {
    console.log(id);
    this.router.navigate(['/section/'+id],{queryParams: {sectionId: id},skipLocationChange:true})
  }

}