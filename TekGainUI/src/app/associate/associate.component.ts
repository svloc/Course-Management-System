import { Component, OnInit, Input,  } from '@angular/core';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { AssociateService } from '../associate.service';
import { Associate } from '../associate';

@Component({
  selector: 'app-associate',
  templateUrl: './associate.component.html',
  styleUrls: ['./associate.component.css']
})
export class AssociateComponent implements OnInit {

  associateId:string='';
  associateName:string='';
  associateAddress:string='';
  associateEmailId:string='';


   message:string=''; 
   error:string='';

 
 @Input() associate:any=new Associate('','','',''); 
 associateModel:any=new Associate('','','',''); 

   
 // instructorNames =[];
 associates: Array<any>=[];
 associatesById: Array<any>=[];
 paramFlag=1;
 sub:any="";

 @Input()title:string='';

 ngOnInit() {

   
 //  fill the code
  
}


ngOnDestroy() {
 
 //  fill the code
 
}

 constructor(private associateService: AssociateService,private router: Router,private _Activatedroute:ActivatedRoute) { }
 
 
 addAssociate() : void {
 
 //  fill the code
 
 }


updateCourse() : void {

 //  fill the code
 
 }

 viewAssociates() : void {  
  
 //  fill the code
 
 }

 

 

}

