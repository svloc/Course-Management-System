import { Component, OnInit, Input, } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from '../course.service';
import { Course } from '../course';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {
  courseId: string = '';
  courseName: string = '';
  fees: number = 0;
  rating: number = 0;
  duration: number = 0;
  courseType: string = '';

  message: string = '';
  instructorNames: string = '';
  error: string = '';

  @Input() course: any = new Course('', '', 0, 0, '', 0);
  courseModel: any = new Course('', '', 0, 0, '', 0);

  courses: Array<any> = [];
  courseById: Array<Course> = [];
  ratings: Array<Course> = [];

  flag1 = 0;
  flag2 = 0;
  paramFlag = 1;
  sub: any = "";

  @Input() title: string = '';

  ngOnInit() {
    this._Activatedroute.queryParams.subscribe(params => {
      this.paramFlag = params.paramFlag;
    });
    this.viewAll()
  }

  ngOnDestroy() {

    //  fill the code

  }

  constructor(private courseService: CourseService, private router: Router, private _Activatedroute: ActivatedRoute) { }

  addCourse(): void {
    this.courseService.addCourse(this.courseModel).subscribe(
      (res) => {
        this.message = "Course added successfully";
        this.viewAll()
        this.courseModel = new Course('', '', 0, 0, '', 0);
        this.error = '';
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );
  }

  updateCourse(): void {
    this.courseService.updateCourse(this.courseId, this.duration).subscribe(
      (res) => {
        this.viewAll()
        this.message = "Course updated successfully";
        this.courseId = '';
        this.duration = 0;
        this.error = '';
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }

  viewCourseById(): void {
    const course = this.courses.find((c) => c.courseId === this.courseId);
    if (course) {
      // If a course with the given courseId is found
      this.courseById = [course]; // Set the course object to a new array
      this.courseId = '';
    } else {
      this.message = '';
      this.error = 'Course not found';
    }
  }

  viewRatings() {
    const course = this.courses.find((c) => c.courseId === this.courseId);
    if (course) {
      // If a course with the given courseId is found
      this.ratings = [course]; // Set the course object to a new array
      this.courseId = '';
      this.error = '';
    } else {
      this.message = '';
      this.error = 'Course not found';
    }

  }

  disableCourse(): void {
    this.courseService.disableCourse(this.courseId).subscribe(
      (res) => {
        this.viewAll()
        this.message = "Course Disabled Successfully";
        this.courseId = '';
        this.error = '';
      },
      (error) => {
        this.message = '';
        this.error = "No admissions found for the given course ID.";
      }
    );

  }

  viewAll():void{
    this.courseService.viewAllCourses().subscribe(
      (res) => {
        this.courses = res;
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
