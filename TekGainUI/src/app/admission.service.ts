import { Injectable } from '@angular/core';

import { HttpClient,HttpErrorResponse,HttpHeaders,HttpInterceptor } from '@angular/common/http';
import { observable, Observable,throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root'
})

export class AdmissionService {
returnMsg:any='';
  constructor(private http: HttpClient,private authService:AuthService) { }


  registration(associateId:string,courseId:string):Observable<Object>{
   
 //  fill the code
return this.returnMsg;
  }

  calculateFees( associateId:String){
     
 //  fill the code
 
 return this.returnMsg;
  }

  addFeedback( regNo:string,  feedback:string,feedbackRating:number) {

   
 //  fill the code
return this.returnMsg;
   }

  highestFeeForTheRegisteredCourse(associateId:string){

return this.returnMsg;
 //  fill the code
  }

 viewFeedbackByCourseId( courseId:string){


 //  fill the code
 return this.returnMsg;
 
 }
 
 viewAllAdmissions(): Observable<any> {
   
     
 //  fill the code
return this.returnMsg;
  }

   
}
