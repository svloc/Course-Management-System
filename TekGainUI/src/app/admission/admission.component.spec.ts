// import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmissionComponent } from './admission.component';
import { async, ComponentFixture, TestBed,inject,tick,fakeAsync, getTestBed } from '@angular/core/testing';
// import { TestBed, async } from '@angular/core/testing';
import {RouterTestingModule,} from "@angular/router/testing";
import {Router,ActivatedRoute} from "@angular/router";
import { HttpClient, HttpHandler ,HttpResponse } from '@angular/common/http';
import { CourseService } from '../course.service';
import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AppComponent } from '../app.component';
import { AppModule,routes } from '../app.module';
import {Location} from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule }   from '@angular/forms';
import { from, Observable } from 'rxjs';
import { AssociateComponent } from '../associate/associate.component';
import { of } from 'rxjs';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { Admission } from '../admission';
import {HttpTestingController} from '@angular/common/http/testing';
import { AssociateService } from '../associate.service';
import { AdmissionService } from '../admission.service';
describe('AdmissionComponent', () => {
  let component: AdmissionComponent;
  let fixture: ComponentFixture<AdmissionComponent>;
  let fixture1:any,fixture2:any,fixture3:any,app1:any,app2:any,app3:any;
  let httpMock: HttpTestingController;
 let queryParams=new Map;
 let admissionService:AdmissionService;
 let service:AdmissionService;
 let admissionModel =new Admission('','','',0,'',0); 
 let httpClient: HttpClient;
 var router: Router;
 var location: Location;
 const compiled:any ='';
 
 const mockAdmission=[ { registrationId:"R101",courseId: "1122", associateId: "A201", fees: 27500,feedback:"Beginner level course",rating:5},  
 { registrationId:"R102",courseId: "1123", associateId: "A202", fees: 27500,feedback:"Intermediate level course",rating:4},
 { registrationId:"R103",courseId: "1122", associateId: "A203", fees: 37500,feedback:"Intermediate level course",rating:3},
 { registrationId:"R104",courseId: "1125", associateId: "A204", fees: 47500,feedback:"Intermediate level course",rating:5},
]

;

beforeEach(async(() => {
   
    TestBed.configureTestingModule({
      
      declarations: [ AdmissionComponent  ],
      imports: [FormsModule,RouterTestingModule.withRoutes(routes), HttpClientTestingModule,BrowserAnimationsModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    // providers: 
    // providers: [CourseService,HttpClient,HttpHandler,
	providers: [AdmissionService,HttpClientTestingModule   
      ], 
     
   }).compileComponents();
  
  }));

  beforeEach(() => {	 	  	    	    	     	      	 	
 
        fixture1 = TestBed.createComponent(AppComponent);

        app1 = fixture1.debugElement.componentInstance;
        fixture2 = TestBed.createComponent(AdmissionComponent);
        app2 = fixture2.debugElement.componentInstance;    
        // httpMock = TestBed.get(HttpTestingController);
        httpClient = TestBed.inject(HttpClient);
        httpMock = TestBed.inject(HttpTestingController);
        admissionService = TestBed.inject(AdmissionService);
        service = TestBed.inject(AdmissionService);
        router = TestBed.get(Router); 
        location = TestBed.get(Location)
  }); 

  

  it('Check for highestFee functionality', fakeAsync (() => {	 	  	    	    	     	      	 	
   
  }));
  

  it('Check for viewFeedbackByCourseId functionality', fakeAsync (() => {
    
  }));
  


});
	 	  	    	    	     	      	 	
