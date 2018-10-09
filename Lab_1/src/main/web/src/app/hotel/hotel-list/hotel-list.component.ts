import { Component, OnInit } from '@angular/core';
import { HotelService } from '../hotels.service';
import { Hotel } from '../hotel.model';
import { UserService } from '../../user/user.service';
import { PagerService } from './pager.service';
import { Type } from '../../user/user.model';

declare var $: any;

@Component({
  selector: 'hotel-list',
  templateUrl: './hotel-list.component.html',
  styleUrls: ['./hotel-list.component.css'],
  providers: [ HotelService ]
})
export class HotelListComponent implements OnInit {

  currentHotel : Hotel;
  pager: any = {};
  pagedItems: any[];

  constructor( private hotelService : HotelService,
               private userService: UserService,
               private pagerService: PagerService) {
  }

  ngOnInit() {
    this.setPage(1);
  }

  popup( action:string, hotel: Hotel ){
    this.currentHotel = hotel;
    $('#hotel-popup').modal();
  }


  setPage(page: number) {
      this.pager = this.pagerService.getPager(this.hotelService.hotels.length, page);
      this.pagedItems = this.hotelService.hotels.slice(this.pager.startIndex, this.pager.endIndex + 1);
  }

  is(a:any){
    if (!a){ return false; }
    if (typeof a === 'undefined') { return false; }
    return true;
  }


}


