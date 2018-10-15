import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import { Hotel } from './hotel/hotel.model';
import { User } from './user/user.model';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private http: HttpClient) { }

  endpoint: string = 'http://localhost:9090/';
  httpOptions: object = {
      headers: new HttpHeaders({
          'Content-Type':  'application/json'
      })
  };

  private extractData(res: Response) {
    let body = res;
    return body || { };
  };

  /* ===============Hotels management=============== */
  getHotels(): Observable<any> {
      return this.http.get(this.endpoint + 'hotels').pipe(
          map(this.extractData));
  };

  getHotelsByPage(id) {
        return this.http.get(this.endpoint + 'hotels/page/' + id);
    };

  getHotel(id): Observable<any> {
      return this.http.get(this.endpoint + 'hotels/' + id).pipe(
          map(this.extractData));
  };

  createHotel(hotel: Hotel) {
      return this.http.post<Hotel>(this.endpoint + 'hotels/', hotel, this.httpOptions).subscribe(
         error =>{
             console.log(error)
         });
  }

  updateHotel(id, hotel: Object) {
      return this.http.put(this.endpoint + 'hotels/' + id, hotel, this.httpOptions).subscribe(
         error =>{
             console.log(error)
         });
  };

  deleteHotel(id) {
      return this.http.delete(this.endpoint + 'hotels/' + id,
      this.httpOptions).subscribe();
  };

  /* ===============Users management=============== */
    getUsers(): Observable<any> {
        return this.http.get(this.endpoint + 'users').pipe(
            map(this.extractData));
    };

    getUser(login): Observable<any> {
        return this.http.get(this.endpoint + 'users/' + login).pipe(
            map(this.extractData));
    };

    createUser(user: User) {
        return this.http.post<User>(this.endpoint + 'users/', user, this.httpOptions).subscribe(
           error =>{
               console.log(error)
           });
    }
}
