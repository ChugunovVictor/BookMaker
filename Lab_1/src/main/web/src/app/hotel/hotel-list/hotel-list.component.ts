import { Component, OnInit, AfterContentInit } from '@angular/core';
import { HotelService } from '../hotels.service';
import { Hotel } from '../hotel.model';
import { UserService } from '../../user/user.service';
import { Type } from '../../user/user.model';
import { RestService } from '../../rest.service';

declare var $: any;

@Component({
  selector: 'hotel-list',
  templateUrl: './hotel-list.component.html',
  styleUrls: ['./hotel-list.component.css'],
  providers: [ HotelService ]
})
export class HotelListComponent implements  OnInit {

  userType = Type;
  currentHotel : Hotel;
  pager: any = {};
  pagedItems: any[];

  totalItems: number;
  currentPage: number = 1;
  pageSize: number;
  totalPages: number;
  pages: any[];
  items: any[];

  constructor( private hotelService : HotelService,
               private userService: UserService,
               private rest: RestService) {
      this.setPage(this.currentPage);
  }

  popup( hotel: Hotel ){
    this.currentHotel = hotel;
    $('#hotel-popup').modal();
  }

  deleteHotel( hotel: Hotel ){
      this.hotelService.deleteHotel(hotel);
  }

  setPage(pageNumber: number) {
     this.rest.getHotelsByPage(pageNumber)
        .subscribe(data => {


           //console.log(data);
               this.totalItems= data['totalElements'];
               this.currentPage= pageNumber;
               this.pageSize= data['size'];
               this.totalPages= data['totalPages'];
               this.pages= [1,2,3,4,5];
               this.items= data['content'];
           }
        );
  }

  is(a:any){
    if (!a){ return false; }
    if (typeof a === 'undefined') { return false; }
    return true;
  }

  ngOnInit(){}
}


