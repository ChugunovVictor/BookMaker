import { Component, OnInit, Input } from '@angular/core';
import { Hotel } from '../hotel.model'
import { Rating } from '../rating.rating.component'

@Component({
  selector: 'hotel-edit',
  templateUrl: './hotel-edit.component.html',
  styleUrls: ['./hotel-edit.component.css']
})
export class HotelEditComponent implements OnInit {

  @ViewChild('name') name: ElementRef;
  @ViewChild('stars') stars: ElementRef;
  @ViewChild('stars_c') stars_c: Rating;

  constructor() {
  }

  ngOnInit() {

  }

  createHotel(){

  }
}
