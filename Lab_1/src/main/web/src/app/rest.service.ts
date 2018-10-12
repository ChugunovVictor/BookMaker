import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

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

  /* Hotels management */
  getHotels(): Observable<any> {
      return this.http.get(this.endpoint + 'hotels').pipe(
          map(this.extractData));
  };

  getHotel(id): Observable<any> {
      return this.http.get(this.endpoint + 'hotels/' + id).pipe(
          map(this.extractData));
  };

  createHotel(hotel: Object): Observable<Object> {
      return this.http.post(this.endpoint + 'hotels/', hotel);
  }

  updateHotel(id, hotel: Object): Observable<any> {
      return this.http.put(this.endpoint + 'hotels/' + id, hotel);
  };

  deleteHotel(id): Observable<any> {
      return this.http.delete(this.endpoint + 'hotels/' + id,
          { responseType: 'text' });
  };
}
