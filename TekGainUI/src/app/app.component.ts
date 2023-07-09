import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'TekGain';

  constructor(private authService: AuthService) { }
  public flag = false;
  ngOnInit(): void {
    this.authService.logstatus.subscribe((status: string) => {
      this.flag = (status === 'loggedIn');
    });

    // Check if there is a token in local storage
    if (this.authService.getToken()) {
      this.authService.logstatus.emit('loggedIn');
    }
  }

}


