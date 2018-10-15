import { Component, OnInit} from '@angular/core';
import { RatingComponent } from '../rating/rating.component'
import { Hotel } from '../hotel.model';
import { RestService } from '../../rest.service';
import { Router} from '@angular/router';

@Component({
  selector: 'hotel-create',
  templateUrl: './hotel-create.component.html',
  styleUrls: ['./hotel-create.component.css']
})
export class HotelCreateComponent implements OnInit {
  h: Hotel = new Hotel();

  constructor( private rest : RestService ,  private router: Router) { }



  ngOnInit() {
  }

  createHotel(){
      console.log( this.rest.createHotel(this.h) );
      this.router.navigate(['/']);
  }

}
