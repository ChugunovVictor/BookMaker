import { Component, OnInit,  Input } from '@angular/core';
import { User } from '../user.model';

@Component({
  selector: 'user-browse',
  templateUrl: './user-browse.component.html',
  styleUrls: ['./user-browse.component.css']
})
export class UserBrowseComponent implements OnInit {

  @Input() user: User;

  constructor() { }

  ngOnInit() {
  }

}
