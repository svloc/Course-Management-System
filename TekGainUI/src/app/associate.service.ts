
import { Associate } from './associate';
import { Component, OnInit, Input, } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpInterceptor } from '@angular/common/http';
import { observable, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class AssociateService {
  host: string = '';
  token: any = "";
  returnMsg: any = '';
  public api_associate: string = '';

  constructor(private http: HttpClient, private authService: AuthService) {
    this.api_associate = environment.apiUrl;
  }

  addAssociate(associate: Object): Observable<Object> {
    this.token=localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });

      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.post(this.api_associate + '/associate/addAssociate', associate, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }

  updateAssociate(associateId: string, associateAddress: string): Observable<Object> {
    this.token=localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      const associate = { associateId:associateId,associateAddress: associateAddress };
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.put(this.api_associate + `/associate/updateAssociate/${associateId}/${associateAddress}`, associate,{ headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }

  viewAssociates(): Observable<any> {
    this.token=localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.get(this.api_associate + '/associate/viewAll', { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }

  }

}
