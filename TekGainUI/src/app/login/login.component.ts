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
      const trimmedUsername = this.user.username?.trim();
      const trimmedPassword = this.user.password?.trim();

      if (!trimmedUsername || !trimmedPassword) {
         this.errorMsg = "Username and password cannot be empty.";
         return;
      }

      this.user.username = trimmedUsername;
      this.user.password = trimmedPassword;

      this.authService.login(this.user)
         .then((loggedIn: boolean) => {
            if (!loggedIn) {
               this.errorMsg = "Invalid username/password";
            } else {
               this.errorMsg = "";
            }
         })
         .catch((error: any) => {
            this.errorMsg = "An unexpected error occurred during login.";
         });
   }
}