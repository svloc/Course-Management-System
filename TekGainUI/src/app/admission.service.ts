import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse, HttpHeaders, HttpInterceptor } from '@angular/common/http';
import { observable, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})

export class AdmissionService {

  public api_admission: string = '';
  token: string = '';
  returnMsg: any = '';

  constructor(private http: HttpClient, private authService: AuthService) {
    this.api_admission = environment.apiUrl + '/admission';
    this.token = this.authService.getToken(); // Retrieve the token from local storage
  }

  private createAuthHeaders(): HttpHeaders {
    return new HttpHeaders({ 'Authorization': `Bearer ${this.token}` });
  }

  //1
  registration(associateId: string, courseId: string): Observable<Object> {
    try {
      const headers = this.createAuthHeaders();
      const admission = { associateId: associateId, courseId: courseId }
      return this.http.post(this.api_admission + `/register/${associateId}/${courseId}`, admission, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }

  //2
  calculateFees(associateId: String) {
    try {
      const headers = this.createAuthHeaders();
      const admission = { associateId: associateId }
      return this.http.put(this.api_admission + '/calculateFees/' + associateId, admission, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }

  //3
  addFeedback(regNo: string, feedback: string, feedbackRating: number) {
    try {
      const headers = this.createAuthHeaders();
      const admission = { registrationId: regNo, feedback: feedback, rating: feedbackRating }
      return this.http.post(this.api_admission + `/feedback/${regNo},${feedback},${feedbackRating}`, admission, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }

  }

  //4
  highestFeeForTheRegisteredCourse(associateId: string) {
    try {
      const headers = this.createAuthHeaders();
      return this.http.get(this.api_admission + `/highestFee/${associateId}`, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }

  //5
  viewFeedbackByCourseId(courseId: string) {
    try {
      const headers = this.createAuthHeaders();
      return this.http.get(this.api_admission + `/viewFeedbackByCourseId/${courseId}`, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }
  //6
  viewAllAdmissions(): Observable<any> {
    try {
      const headers = this.createAuthHeaders();
      return this.http.get(this.api_admission + '/viewAll', { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }
  //7
  makePayment(registrationId: string, fees: number): Observable<any> {
    try {
      const headers = this.createAuthHeaders();
      const admission = { registrationId: registrationId }
      return this.http.post(this.api_admission + `/makePayment/${registrationId}/${fees}`, admission, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }
}