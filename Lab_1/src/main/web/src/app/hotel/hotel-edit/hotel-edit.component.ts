import { Component, OnInit, Input } from '@angular/core';
import { Hotel } from '../hotel.model'
import { RestService } from '../../rest.service';

@Component({
  selector: 'hotel-edit',
  templateUrl: './hotel-edit.component.html',
  styleUrls: ['./hotel-edit.component.css']
})
export class HotelEditComponent implements OnInit {

  @Input() hotel: Hotel;

  constructor(private rest : RestService ) {
  }

  ngOnInit() {

  }

  update(){
     this.rest.updateHotel(this.hotel.id, this.hotel);
  }
}
