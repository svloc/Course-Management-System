import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpInterceptor } from '@angular/common/http';
import { observable, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class CourseService {

  host: string = '';
  token: any = "";
  returnMsg: any = '';

  public api_course: string = '';

  ngOnInit() {
      // Retrieve the token from local storage
  }

  constructor(private http: HttpClient, private authService: AuthService) {
    this.api_course = environment.apiUrl;
  }

  addCourse(course: Object): Observable<Object> {
    try {
      this.token=localStorage.getItem('accessToken');
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });

      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.post(this.api_course + '/course/addCourse', course, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }


  // updateCourse(courseId: string, duration: number): Observable<Object> {
  //   try {
  //     this.token=localStorage.getItem('accessToken');
  //     // Set the headers with the authorization bearer token
  //     const headers = new HttpHeaders({
  //       'Authorization': `Bearer ${this.token}`
  //     });
  //     // Send a PUT request to the addCourse URL with the course object and headers
  //     return this.http.put(this.api_course + `/course/update/${courseId}/${duration}`, { headers });
  //   } catch (error) {
  //     console.log("An unexpected error occurred while updating the course: ", error);
  //     return null;
  //   }
  // }

  updateCourse(courseId: string, duration: number): Observable<Object> {
    try {
      this.token = localStorage.getItem('accessToken');
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      // Create the course object to be updated
      const course = { courseId:courseId,duration: duration };
      // Send a PUT request to update the course with the course object and headers
      return this.http.put(this.api_course + `/course/update/${courseId}/${duration}`,course, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while updating the course: ", error);
      return null;
    }
  }
  
  viewAllCourses(): Observable<any> {
    this.token=localStorage.getItem('accessToken');
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.get(this.api_course + '/course/viewAll', { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }

  }

  disableCourse(courseId: string): Observable<Object> {
    try {
      // Set the headers with the authorization bearer token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      });
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.delete(this.api_course + '/course/deactivateCourse/'+courseId, { headers });
    } catch (error) {
      console.log("An unexpected error occurred while adding the course: ", error);
      return null;
    }
  }
}
