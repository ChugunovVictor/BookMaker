import { Component, OnInit, ElementRef, ViewChild} from '@angular/core';
import { UserService } from '../user.service';
import {User} from '../user.model';
import { Router} from '@angular/router';


@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

   @ViewChild ("selectUser") selectUser: ElementRef;
   @ViewChild ("enterUser") enterUser: ElementRef;
   @ViewChild ("enterPassword") enterPassword: ElementRef;

  constructor(private userService:UserService, private router: Router) { }

  ngOnInit() {
  }

  login(){
    if( this.enterUser.nativeElement.value === '' ){
      this.checkPassword()
    }else{
      this.registerNewUser()
    }/*
     ;*/

  }

  checkPassword(){
    var loginString = this.enterUser.nativeElement.value === '' ?
                      this.selectUser.nativeElement.value :
                      this.enterUser.nativeElement.value;
    var user = this.userService.findUser(loginString)
    this.loginAccept(user);
  }

  registerNewUser(){
    var loginString = this.enterUser.nativeElement.value;
    if( this.userService.findUser( loginString ) !== null ){
      this.checkPassword();
    } else {

    }
  }

  loginAccept(user: User){
        this.userService.currentUser = user;
        this.router.navigate(['']);
  }

}
