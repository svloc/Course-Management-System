import { Component } from '@angular/core';
import { AuthService } from './auth.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'TekGain';
  constructor(private authService: AuthService) { }
  flag = false;
  ngOnInit(): void {
    //  fill the code
    if (this.authService.getToken()) {
      this.flag = true;
    }

  }

}


