import { Component, OnInit } from '@angular/core';
import { UserService } from './user/user.service';
import { Router} from '@angular/router';
import { Type } from './user/user.model';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Lab_1';
  userType = Type;

  constructor(private userService: UserService, private router: Router){

  }

  ngOnInit(){
    if( !this.userService.currentUser )
      this.router.navigate(['login']);
  }

  logout(event){
    event.target.parentElement.className='dropdown-menu';
    this.userService.currentUser = null;
    this.router.navigate(['login']);
  }

  is(a:any){
      if (!a){ return false; }
      if (typeof a === 'undefined') { return false; }
      return true;
    }

}
