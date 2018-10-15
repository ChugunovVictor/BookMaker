import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Hotel } from './hotel.model'
import { RestService } from '../rest.service';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';

@Injectable()
export class HotelService {
  hotels: Hotel[] = [];

  constructor( private http: HttpClient, public restService:RestService,
    private route: ActivatedRoute, private router: Router) {
      restService.getHotels().subscribe((data:any[]) => {
          data.forEach( (data:any[]) => {
              this.hotels.push(new Hotel(data['id'], data['name'], data['stars']));
          })
      });

      router.routeReuseStrategy.shouldReuseRoute = function(){
          return false;
      };

      router.events.subscribe((evt) => {
          if (evt instanceof NavigationEnd) {
              this.router.navigated = false;
              window.scrollTo(0, 0);
          }
      });
  }

  deleteHotel(hotel: Hotel){
    var resp = this.restService.deleteHotel(hotel.id);
    console.log(resp);
    /* dont know how to refresh it clearly */
    this.router.navigate(['']);
  }
}
