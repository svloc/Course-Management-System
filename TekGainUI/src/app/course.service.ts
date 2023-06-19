import { Injectable } from '@angular/core';
import { HttpClient,HttpErrorResponse,HttpHeaders,HttpInterceptor } from '@angular/common/http';
import { observable, Observable,throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})

export class CourseService {

  host:string='';
  token:any="";
  returnMsg:any='';
 
  ngOnInit() {
    
  }

  constructor(private http: HttpClient,private authService:AuthService) {
  }
  addCourse(course:Object):Observable<Object>{
   
  return this.returnMsg;
 //  fill the code
  }
 
  updateCourse(courseId:string,duration:number):Observable<Object>{
  
 //  fill the code
 return this.returnMsg;
   }  

  viewAllCourses(): Observable<any> {
   
   
 //  fill the code
return this.returnMsg;
       
    }

disableCourse(courseId:string):Observable<Object>{
  
 //  fill the code
 return this.returnMsg;
}


   

}
