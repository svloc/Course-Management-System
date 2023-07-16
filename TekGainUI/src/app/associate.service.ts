
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

  constructor(private http: HttpClient, private authService: AuthService) {
    this.host = environment.apiUrl + '/associate';
    this.token = localStorage.getItem('accessToken');
  }

  private createAuthHeaders(): HttpHeaders {
    return new HttpHeaders({ 'Authorization': `Bearer ${this.token}` });
  }

  addAssociate(associate: Object): Observable<Object> {
    const headers = this.createAuthHeaders();
    try {
      return this.http.post(`${this.host}/addAssociate`, associate, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }

  updateAssociate(associateId: string, associateAddress: string): Observable<Object> {
    const headers = this.createAuthHeaders();
    const associate = { associateId, associateAddress };
    try {
      return this.http.put(`${this.host}/updateAssociate/${associateId}/${associateAddress}`, associate, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }

  viewAssociates(): Observable<any> {
    const headers = this.createAuthHeaders();
    try {
      return this.http.get(`${this.host}/viewAll`, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }
}