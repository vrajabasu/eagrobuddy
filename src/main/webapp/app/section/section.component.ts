import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.css']
})
export class SectionComponent implements OnInit {
  sectionId: number;
  headerHeight;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.headerHeight = document.getElementById('header').offsetHeight;
    this.activatedRoute.params.subscribe(params => {
      this.sectionId = params['sectionId'];
      console.log(this.sectionId);
    }
    )
  }

}
