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

  ngOnInit() {
    // Retrieve the token from local storage
  }

  constructor(private http: HttpClient, private authService: AuthService) {
    this.host = environment.apiUrl + '/course';
    this.token = localStorage.getItem('accessToken');
  }
  private createAuthHeaders(): HttpHeaders {
    return new HttpHeaders({ 'Authorization': `Bearer ${this.token}` });
  }

  addCourse(course: Object): Observable<Object> {
    try {
      const headers = this.createAuthHeaders();
      return this.http.post(this.host + '/addCourse', course, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }

  updateCourse(courseId: string, duration: number): Observable<Object> {
    try {
      const headers = this.createAuthHeaders();
      const course = { courseId: courseId, duration: duration };
      // Send a PUT request to update the course with the course object and headers
      return this.http.put(this.host + `/update/${courseId}/${duration}`, course, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }

  viewAllCourses(): Observable<any> {
    this.token = localStorage.getItem('accessToken');
    try {
      const headers = this.createAuthHeaders();
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.get(this.host + '/viewAll', { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }

  }

  disableCourse(courseId: string): Observable<Object> {
    try {
      const headers = this.createAuthHeaders();
      // Send a POST request to the addCourse URL with the course object and headers
      return this.http.delete(this.host + '/deactivateCourse/' + courseId, { headers });
    } catch (error) {
      return throwError('Something went wrong. Please try again later.');
    }
  }
}
