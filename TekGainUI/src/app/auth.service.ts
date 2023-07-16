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
  constructor(private http: HttpClient) { }
  token: any = [];
  logstatus: any = new EventEmitter<string>();

  loginUrl: string = "http://localhost:9098/app";

  login(user: User): Promise<boolean> {
    const loginUrl = this.loginUrl + '/signin';
    return this.http.post(loginUrl, user).toPromise()
      .then((res: any) => {
        localStorage.setItem('accessToken', res.accessToken);
        localStorage.setItem('roles',res.roles);
        this.logstatus.emit('loggedIn');
        return true; // Return true to indicate successful login
      }, (error) => { console.log("error==>", error); return false; })
      .catch((error: any) => {
        return false; // Return false to indicate failed login
      });
  }
  getToken(): any {
    return localStorage.getItem("accessToken");
  }
  logout(): void {
    localStorage.removeItem('roles');
    localStorage.removeItem("accessToken");
  }
}