export enum Type{
  ADMIN=1,
  USER=2
}

export class User {


  icon: any;
  id: number;
  login: string;
  password: string;
  type : Type;

  constructor(id: number, login: string, password: string, type: Type, icon: any){
    this.id = id;
    this.login = login;
    this.password = password;
    this.type = type;
    this.icon = icon;
  }
}
