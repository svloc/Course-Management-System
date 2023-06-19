import { tap, delay } from 'rxjs/operators';
import { Injectable, EventEmitter } from '@angular/core';
import { User } from './user';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { observable, Observable, of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  token: any = [];
  logstatus: any = new EventEmitter<string>();
  loginUrl:string="http://localhost:9098/app";

  login(user: Object): Promise<boolean> {
    return this.http.post<any>(`${this.loginUrl}/signin`, user).pipe(
      tap(response => {
        if (response && response.token) {
          localStorage.setItem('authToken', response.token);
        }
      })
    ).toPromise().then(() => true).catch(() => false);
  }
  

  getToken(): any {

    //  fill the code
    return localStorage.getItem("authToken");
  }


  logout(): void {


    //  fill the code
    localStorage.removeItem("authToken");

  }

  constructor(private http: HttpClient) {


  }
}