import { Component, OnInit } from '@angular/core';
import { UserService } from './user/user.service';
import { Router} from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Lab_1';

  constructor(private userService: UserService, private router: Router){

  }

  ngOnInit(){
    if( !this.userService.currentUser )
      this.router.navigate(['login']);
  }

  logout(){
    this.userService.currentUser = null;
    this.router.navigate(['login']);
  }

}
