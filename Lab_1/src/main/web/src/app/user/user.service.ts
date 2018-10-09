import { Injectable } from '@angular/core';
import { Type, User } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  users: User[] = [];
  currentUser: User;

  constructor() {
     this.users.push(new User(1, 'ADMINISTRATOR','123',1,''));
     this.users.push(new User(2, 'USER','123',2,''));
  }


}
