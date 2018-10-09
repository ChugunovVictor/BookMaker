import { Component, OnInit,ViewChild, ElementRef} from '@angular/core';
import { RatingComponent } from '../rating/rating.component'

@Component({
  selector: 'hotel-create',
  templateUrl: './hotel-create.component.html',
  styleUrls: ['./hotel-create.component.css']
})
export class HotelCreateComponent implements OnInit {
  @ViewChild('name') name: ElementRef;
  @ViewChild('stars') stars: ElementRef;
  @ViewChild('stars_c') stars_c: RatingComponent;

  constructor() { }

  ngOnInit() {
  }

}
