import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Hotel } from './hotel.model'
import { RestService } from '../rest.service';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable()
export class HotelService {
  hotels: Hotel[] = [];

  constructor( private http: HttpClient, public restService:RestService,
    private route: ActivatedRoute, private router: Router) {
        for (var i=0; i < 45; i++)
          this.hotels.push(new Hotel(i, 'mock_name_'+i, i%15));


        restService.getHotels().subscribe((data:any[]) => {


            data.forEach( (data:any[]) => {
                this.hotels.push(new Hotel(data['id'], data['name'], data['stars']));
            })
        });
  }
}
