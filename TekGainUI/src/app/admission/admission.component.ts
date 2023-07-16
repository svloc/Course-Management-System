import { Component, OnInit, Input, } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

import { AdmissionService } from '../admission.service';
import { Admission } from '../admission';
import { CourseService } from '../course.service';
import { AssociateService } from '../associate.service';

@Component({
  selector: 'app-admission',
  templateUrl: './admission.component.html',
  styleUrls: ['./admission.component.css']
})
export class AdmissionComponent implements OnInit {

  registrationId: string = '';
  courseId: string = '';
  associateId: string = '';
  fees: number = 0;
  feedback: string = '';
  returnfees: any = '';
  message: string = '';
  error: string = '';
  rating: number = 0;
  returnCourseId: any = '';
  @Input() admission: any = new Admission('', '', '', 0, '', 0);
  admissionModel: any = new Admission('', '', '', 0, '', 0);


  admissions: Array<Admission> = [];
  feedbackArray: Array<Admission> = [];
  associates: Array<any> = [];
  courses: Array<any> = [];
  AdmissionsArray = [];
  paramFlag = 1;
  sub: any = "";

  @Input() title: string = '';

  ngOnInit() {
    this._Activatedroute.queryParams.subscribe(params => {
      this.paramFlag = params.paramFlag;
      this.error = '';
      this.message = '';
      this.associateId='';
    });
    this.viewAllAdmissions();
    this.viewAllCourses();
  }

  ngOnDestroy() {
    //  this.sub.unsubscribe();

  }

  constructor(private admissionService: AdmissionService, private router: Router, private _Activatedroute: ActivatedRoute, private courseService: CourseService, private associateService: AssociateService) { }


  registration(): void {
    this.message = '';
    this.error='';
    if (!this.admissionModel.associateId) {
      this.error = "Associate Id is required.";
      return;
    }
    if (!this.admissionModel.courseId) {
      this.error = "Course Id is required.";
      return;
    }
    this.admissionService.registration(this.admissionModel.associateId, this.admissionModel.courseId).subscribe(
      (res: Admission) => {
        this.error = '';
        this.message = "Registration is successful, Your registration id:" + res.registrationId;
        this.admissionModel = new Admission('', '', '', 0, '', 0);
        this.viewAllAdmissions();
      },
      (error) => {
        this.message = '';
        this.error = error.error;
      }
    );

  }

  totalFees() {
    this.message = '';
    this.error='';
    if (!this.associateId) {
      this.error = "Associate Id is required.";
      return;
    }
    this.admissionService.calculateFees(this.associateId).subscribe(
      (res) => {
        this.error = '';
        this.message = "Total Fees : " + res;
        this.associateId='';
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );
  }

  addFeedback(): void {
    this.message = '';
    this.error='';

    if (!this.admissionModel.registrationId) {
      this.error = "Registration Id is required.";
      return;
    }
    if (!this.admissionModel.feedback) {
      this.error = "Feedback is required.";
      return;
    }
    if (!this.admissionModel.rating) {
      this.error = "Rating is required.";
      return;
    }
    if (this.admissionModel.rating < 0 || this.admissionModel.rating > 5) {
      this.error = "Invalid Rating. Rating can be 0 to 5 ";
      return;
    }

    this.admissionService.addFeedback(this.admissionModel.registrationId, this.admissionModel.feedback, this.admissionModel.rating).subscribe(
      (res) => {
        this.message = "Feedback updated successfully";
        this.admissionModel = new Admission('', '', '', 0, '', 0);
        this.error = '';
        this.viewAllAdmissions();
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }


  highestFee(): void {
    this.message = '';
    this.error='';
    if (!this.associateId) {
      this.error = "Associate Id is required.";
      return;
    }
    this.admissionService.highestFeeForTheRegisteredCourse(this.associateId).subscribe(
      (res) => {
        this.error = '';
        this.message = "Course Id "+res +" having Highest Fee"  ;
        this.associateId = '';
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }

  viewFeedbackByCourseId() {
    this.message = '';
    this.error='';
    if (!this.courseId) {
      this.error = "Course Id is required.";
      return;
    }
    this.admissionService.viewFeedbackByCourseId(this.courseId).subscribe(
      (res: Array<Admission>) => {
        this.error = '';
        this.feedbackArray = res;
        this.courseId = '';
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }

  makePayment() {
    this.message = '';
    this.error='';
    if (!this.fees) {
      this.error = "Fees is required.";
      return;
    }
    if (!this.registrationId) {
      this.error = "Registration Id is required.";
      return;
    }
    this.admissionService.makePayment(this.registrationId, this.fees).subscribe(
      (res) => {
        this.registrationId = '';
        this.fees = 0;
        window.open(res.approval_url, '_blank');
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );
  }

  viewAllAdmissions(): void {
    this.admissionService.viewAllAdmissions().subscribe(
      (res: Array<Admission>) => {
        this.admissions = res;

        this.AdmissionsArray = res.reduce((uniqueAdmissions, admission) => {
          const existingAdmission = uniqueAdmissions.find(a => a.courseId === admission.courseId);
          if (!existingAdmission) {
            uniqueAdmissions.push(admission);
          }
          return uniqueAdmissions;
        }, []);

        this.courseId = '';
        this.viewAllAssociates(res);
      });
  }


  viewAllAssociates(admissionData): void {
    this.associateService.viewAssociates().subscribe(
      (associates) => {
        const filteredAssociates = associates.filter(
          (associate) => !admissionData.some((admiss) => admiss.associateId === associate.associateId)
        );
        console.log("filteredAssociates: ", filteredAssociates);
        this.associates = filteredAssociates;
      }
    );
  }
  viewAllCourses(): void {
    this.courseService.viewAllCourses().subscribe(
      (res) => {
        this.courses = res;
      }
    );
  }

  access(roles:string[]){
    const currentUser=localStorage.getItem('roles');
    return roles.some(x => x ==currentUser);
  }

}
