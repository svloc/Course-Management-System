import { Component, OnInit, Input, } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

import { AdmissionService } from '../admission.service';
import { Admission } from '../admission';

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


  admissions: Array<any> = [];
  feedbackArray: Array<Admission> = [];
  AdmissionsArray = [];
  paramFlag = 1;
  sub: any = "";

  @Input() title: string = '';

  ngOnInit() {
    this._Activatedroute.queryParams.subscribe(params => {
      this.paramFlag = params.paramFlag;
    });

    this.admissionService.viewAllAdmissions().subscribe(
      (res) => {
        this.admissions = res;
      });
  }

  ngOnDestroy() {
    //  this.sub.unsubscribe();

  }

  constructor(private admissionService: AdmissionService, private router: Router, private _Activatedroute: ActivatedRoute) { }


  registration(): void {
    this.admissionService.registration(this.admissionModel.associateId, this.admissionModel.courseId).subscribe(
      (res:Admission) => {
        this.error ='';
        this.message = "Registration is successful, Your registration id:"+res.registrationId;
        this.admissionModel = new Admission('', '', '', 0, '', 0);
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }

  totalFees() {
    this.admissionService.calculateFees(this.associateId).subscribe(
      (res) => {
        this.error ='';
        this.message = "Total Fees : "+res;
        this.admission = new Admission('', '', '', 0, '', 0);
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );
  }

  addFeedback(): void {
    this.admissionService.addFeedback(this.admission.registrationId, this.admission.feedback, this.admission.rating).subscribe(
      (res) => {
        this.message = "Feedback updated successfully";
        this.admission = new Admission('', '', '', 0, '', 0);
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }


  highestFee(): void {
    this.admissionService.highestFeeForTheRegisteredCourse(this.admission.associateId).subscribe(
      (res) => {
        this.error ='';
        this.message = "Course having Highest Fee: "+res;
        this.admission = new Admission('', '', '', 0, '', 0);
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }

  viewFeedbackByCourseId() {
    this.admissionService.viewFeedbackByCourseId(this.admission.courseId).subscribe(
      (res: Array<Admission>) => {
        this.feedbackArray = res;
        this.admission = new Admission('', '', '', 0, '', 0);
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }

  makePayment() {
    this.admissionService.makePayment(this.admission.registrationId,this.fees).subscribe(
      (res) => {
        this.admission = new Admission('', '', '', 0, '', 0);
        this.fees=0;
        window.open(res.approval_url, '_blank');
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );
  }


}

