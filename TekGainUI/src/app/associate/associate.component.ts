import { Component, OnInit, Input, } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { AssociateService } from '../associate.service';
import { Associate } from '../associate';

@Component({
  selector: 'app-associate',
  templateUrl: './associate.component.html',
  styleUrls: ['./associate.component.css']
})
export class AssociateComponent implements OnInit {

  associateId: string = '';
  associateName: string = '';
  associateAddress: string = '';
  associateEmailId: string = '';
  message: string = '';
  error: string = '';
  @Input() associate: any = new Associate('', '', '', '');
  associateModel: any = new Associate('', '', '', '');
  // instructorNames =[];
  associates: Array<any> = [];
  associatesById: Array<any> = [];
  paramFlag = 1;
  sub: any = "";

  @Input() title: string = '';

  ngOnInit() {
    this._Activatedroute.queryParams.subscribe(params => {
      this.paramFlag = params.paramFlag;
      this.message = '';
      this.error = '';
    });
    this.viewAllAssociates();
  }
  ngOnDestroy() {
    //  fill the code
  }

  constructor(private associateService: AssociateService, private router: Router, private _Activatedroute: ActivatedRoute) { }

  addAssociate(): void {
    this.message = '';
    this.error = '';

    if (!this.associateModel.associateName) {
      this.error = "Associate Name is required.";
      return;
    }
    if (!this.associateModel.associateEmailId) {
      this.error = "Associate EmailId is required.";
      return;
    }
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(this.associateModel.associateEmailId)) {
      this.error = "Invalid email format.";
      return;
    }
    if (!this.associateModel.associateAddress) {
      this.error = "Associate Address is required.";
      return;
    }

    this.associateService.addAssociate(this.associateModel).subscribe(
      (res) => {
        this.error = '';
        this.message = "Associates added successfully";
        this.associateModel = new Associate('', '', '', '');
        this.viewAllAssociates();
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }

  updateCourse(): void {
    this.message = '';
    this.error = '';
    if (!this.associate.associateId) {
      this.error = "Associate Id is required.";
      return;
    }
    if (!this.associate.associateAddress) {
      this.error = "Associate Address is required.";
      return;
    }
    this.associateService.updateAssociate(this.associate.associateId, this.associate.associateAddress).subscribe(
      (res) => {
        this.error = '';
        this.message = "Associates updated successfully";
        this.viewAllAssociates();
        this.associate = new Associate('', '', '', '');
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );
  }

  viewAssociates(): void {
    this.error='';
    this.message='';
    if (!this.associateId) {
      this.error = "Associate Id is required.";
      return;
    }
    const associate = this.associates.find((c) => c.associateId === this.associateId);
    if (associate) {
      // If a associateModel with the given associateId is found
      this.associatesById = [associate]; // Set the course object to a new array
      this.associateId = '';
      this.error = '';
    } else {
      this.message = '';
      this.error = 'Course not found';
    }
  }

  viewAllAssociates(): void {
    this.associateService.viewAssociates().subscribe(
      (res) => {
        this.associates = res;
      });
  }
  
  access(roles:string[]){
    const currentUser=localStorage.getItem('roles');
    return roles.some(x => x ==currentUser);
  }
}
