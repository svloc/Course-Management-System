import { Component, OnInit, Input,  } from '@angular/core';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from '../course.service';
import { Course } from '../course';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {
  courseId:string='';
  courseName:string='';
  fees:number=0;
  rating:number=0;
  duration:number=0;
  courseType:string='';

  message:string='';
  instructorNames:string='';
  error:string='';
  
  @Input() course:any=new Course('','',0,0,'',0); 
  courseModel:any=new Course('','',0,0,'',0);     
  
  courses: Array<any>=[];
  courseById:Array<Course> = []; 
  ratings:Array<Course> = [];  
 
  flag1=0;
  flag2=0;
  paramFlag=1;
  sub:any="";

  @Input()title:string='';

  ngOnInit() {
    this._Activatedroute.queryParams.subscribe(params => {
      this.paramFlag = params.paramFlag;
      //console.log("paramFlag==>",this.paramFlag)
    });
  }

 ngOnDestroy() {
  
 //  fill the code
 
 }
 
  constructor(private courseService: CourseService,private router: Router,private _Activatedroute:ActivatedRoute) { }
  
   addCourse() : void {
 
 //  fill the code
 
   
  }
 

updateCourse() : void {
 
 //  fill the code
 
  }

  
  viewCourseById() : void {  
   
 //  fill the code
 
  }

  viewRatings(){
  
 //  fill the code
 
  }

  disableCourse() : void {
    
 //  fill the code
 
    
  }

 

}
