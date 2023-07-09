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
    this.api_admission = environment.apiUrl;
    this.token = this.authService.getToken(); // Retrieve the token from local storage
  }

  //1
  registration(associateId: string, courseId: string): Observable<Object> {
    this.token = localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      const admission = { associateId: associateId, courseId: courseId }
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.post(this.api_admission + `/admission/register/${associateId}/${courseId}`, admission, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }

  //2
  calculateFees(associateId: String) {
    this.token = localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      const admission = { associateId: associateId }
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.put(this.api_admission + '/admission/calculateFees/' + associateId, admission, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }

  //3
  addFeedback(regNo: string, feedback: string, feedbackRating: number) {
    this.token = localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      const admission = { registrationId: regNo, feedback: feedback, rating: feedbackRating }
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.post(this.api_admission + `/admission/feedback/${regNo},${feedback},${feedbackRating}`, admission, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }

  }

  //4
  highestFeeForTheRegisteredCourse(associateId: string) {
    this.token = localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.get(this.api_admission + `/admission/highestFee/${associateId}`, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }

  //5
  viewFeedbackByCourseId(courseId: string) {
    this.token = localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.get(this.api_admission + `/admission/viewFeedbackByCourseId/${courseId}`, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }
  //6
  viewAllAdmissions(): Observable<any> {
    this.token = localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.get(this.api_admission + '/admission/viewAll', { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }
  //7
  makePayment(registrationId: string,fees:number): Observable<any> {
    this.token = localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      const admission = { registrationId: registrationId }
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.post(this.api_admission + `/admission//makePayment/${registrationId},${fees}` + registrationId, admission, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }



}


