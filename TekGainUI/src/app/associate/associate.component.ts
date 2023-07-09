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
    });
    this.associateService.viewAssociates().subscribe(
      (res) => {
        this.associates = res;
      });
  }


  ngOnDestroy() {

    //  fill the code

  }

  constructor(private associateService: AssociateService, private router: Router, private _Activatedroute: ActivatedRoute) { }


  addAssociate(): void {
    this.associateService.addAssociate(this.associateModel).subscribe(
      (res) => {
        this.message = "Associates added successfully";
        this.associateModel = new Associate('', '', '', '');
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );

  }

  updateCourse(): void {
    this.associateService.updateAssociate(this.associateModel.associateId, this.associateModel.associateAddress).subscribe(
      (res) => {
        this.message = "Associates updated successfully";
        this.associateModel = new Associate('', '', '', '');
      },
      (error) => {
        this.message = '';
        this.error = error.error.error;
      }
    );
  }

  viewAssociates(): void {
    const associate = this.associates.find((c) => c.associateId === this.associateModel.associateId);

    if (associate) {
      // If a associateModel with the given associateId is found
      this.associatesById = [associate]; // Set the course object to a new array
      this.associateModel = new Associate('', '', '', '');
    } else {
      this.message = '';
      this.error = 'Course not found';
    }

  }





}

