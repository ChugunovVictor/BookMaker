enum Type {
      USER = 1,
      ADMINISTRATOR = 2
  };

export class User {


  id: number;
  login: string;
  password: string;
  type: Type;

  constructor(id: number, login: string, password: string, type: Type){
    this.id = id;
    this.login = login;
    this.password = password;
    this.type = type;
  }
}
