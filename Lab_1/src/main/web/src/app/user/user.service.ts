import { Injectable } from '@angular/core';
import { Type, User } from './user.model';
import { HttpClient } from '@angular/common/http';
import { RestService } from '../rest.service';
import { ActivatedRoute, Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  users: User[] = [];
  currentUser: User;

  constructor( private http: HttpClient, public restService:RestService,
     private route: ActivatedRoute, private router: Router) {
         restService.getUsers().subscribe((data:any[]) => {
             data.forEach( (data:any[]) => {
                this.users.push(new User(data['id'], data['login'], data['password'],
                   data['admin'] === false ? Type.USER : Type.ADMIN));
             })
         });
  }


  findUser( login: string ){
     for( let user of this.users ) {
        if( login === user.login ){
          return user;
        }
     }
  }
}





















