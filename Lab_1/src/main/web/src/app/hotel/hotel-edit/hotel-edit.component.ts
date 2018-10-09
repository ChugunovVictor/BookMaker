import { Component, OnInit, Input } from '@angular/core';
import { Hotel } from '../hotel.model'

@Component({
  selector: 'hotel-edit',
  templateUrl: './hotel-edit.component.html',
  styleUrls: ['./hotel-edit.component.css']
})
export class HotelEditComponent implements OnInit {

  @Input() action: string;
  @Input() hotel: Hotel;

  constructor() {
  }

  ngOnInit() {

  }

  createHotel(){

  }
}
