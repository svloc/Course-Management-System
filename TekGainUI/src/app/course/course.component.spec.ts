import { async, ComponentFixture, TestBed,inject,tick,fakeAsync, getTestBed } from '@angular/core/testing';
// import { TestBed, async } from '@angular/core/testing';
import {RouterTestingModule,} from "@angular/router/testing";
import {Router,ActivatedRoute} from "@angular/router";
import { CourseComponent } from './course.component';
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
// import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { Associate } from '../associate';
import { Course } from '../course';
import {HttpTestingController} from '@angular/common/http/testing';
import { AssociateService } from '../associate.service';
describe('Component -', () => {

  let fixture1:any,fixture2:any,fixture3:any,app1:any,app2:any,app3:any;
  let httpMock: HttpTestingController;
 let queryParams=new Map;
 let courseService:CourseService;
 let service:AssociateService;
  let httpClient: HttpClient;
 var router: Router;
 var location: Location;
 const compiled:any ='';
 let mockLoggerSvc: any='';
 

  beforeEach(async(() => {
    mockLoggerSvc = {
      info: jasmine.createSpy("info"),
      success: jasmine.createSpy("success"),
      error: jasmine.createSpy("error")
    };
    TestBed.configureTestingModule({
      
      declarations: [ CourseComponent  ],
      imports: [FormsModule,RouterTestingModule.withRoutes(routes), HttpClientTestingModule,BrowserAnimationsModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
	providers: [CourseService,AssociateService,HttpClientTestingModule   
      ], 
     
   }).compileComponents();
  
  }));

  beforeEach(() => { 
 
        fixture1 = TestBed.createComponent(AppComponent);

        app1 = fixture1.debugElement.componentInstance;
        fixture2 = TestBed.createComponent(CourseComponent);
        app2 = fixture2.debugElement.componentInstance;  
        httpClient = TestBed.inject(HttpClient);
        httpMock = TestBed.inject(HttpTestingController);
        courseService = TestBed.inject(CourseService);
        service = TestBed.inject(AssociateService);
        fixture3 = TestBed.createComponent(CourseComponent);
        app3 = fixture3.componentInstance;
        router = TestBed.get(Router); 
        location = TestBed.get(Location)
  });  


    it('Course check for viewCourseById functionality', fakeAsync (() => {
    //   Fill the code 
    
    }));

    it('Course check for viewRatings functionality', fakeAsync (() => {
     
      
       //   Fill the code 
    }));

 
});
	 	  	  		    	   	 	   	 	
