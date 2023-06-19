import { Component, OnInit, Input,  } from '@angular/core';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';

import { AdmissionService } from '../admission.service';
import { Admission } from '../admission';

@Component({
  selector: 'app-admission',
  templateUrl: './admission.component.html',
  styleUrls: ['./admission.component.css']
})
export class AdmissionComponent implements OnInit {

   registrationId:string='';	
   courseId	:string='';
   associateId:string=''	;
   fees:number=0;	
   feedback	:string='';
   returnfees:any='';
   message:string=''; 
   error:string=''; 
   rating:number=0;
   returnCourseId:any='';
  @Input() admission:any=new Admission('','','',0,'',0); 
  admissionModel:any=new Admission('','','',0,'',0); 
   
 
    admissions: Array<any>=[];
    feedbackArray:Array<Admission> = []; 
    AdmissionsArray= []; 
    paramFlag=1;
    sub:any="";

  @Input()title:string='';

 ngOnInit() {

   
}

ngOnDestroy() {
 //  this.sub.unsubscribe();
  
}

 constructor(private admissionService: AdmissionService,private router: Router,private _Activatedroute:ActivatedRoute) { }
 

 registration() : void {
 
//  fill the code
 }

 totalFees(){
 
 //  fill the code
 }

 addFeedback() : void {
 
 //  fill the code
 
 }

 highestFee() : void {  
  
 //  fill the code
 
 }

 viewFeedbackByCourseId( ){
 
 //  fill the code
 
 }
   
 makePayment(){

 }
 

}

