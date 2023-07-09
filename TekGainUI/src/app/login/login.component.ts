import { Component, OnInit, Input } from '@angular/core';
import { User } from '../user';
import { FormGroup, FormControl } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
@Component({
   selector: 'app-login',
   templateUrl: './login.component.html',
   styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

   @Input() user: any = new User('', '');
   public errorMsg: string = "";

   constructor(private authService: AuthService, private router: Router) { }

   ngOnInit() {

   }

   onClickSubmit() {
      if (!this.authService.login(this.user)) {
         this.errorMsg = "Invalid username/Password";
         return;
      } else {
         this.errorMsg = "";
      }

   }
}