import { Component, OnInit } from '@angular/core';
import { HotelService } from '../hotels.service';
import { Hotel } from '../hotel.model'
declare var $: any;

@Component({
  selector: 'hotel-list',
  templateUrl: './hotel-list.component.html',
  styleUrls: ['./hotel-list.component.css'],
  providers: [ HotelService ]
})
export class HotelListComponent implements OnInit {

  currentHotel : Hotel;

  constructor( private hotelService : HotelService ) {
  }

  ngOnInit() {
  }

  popup( action:string, hotel: Hotel ){
    this.currentHotel = hotel;
    $('#hotel-popup').modal();
  }
}
