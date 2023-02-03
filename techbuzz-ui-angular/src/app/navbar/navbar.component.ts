import { Component } from '@angular/core';
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private authService: AuthService) {
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  loginUserName() : string {
    return this.authService.loginUserName();
  }

  logout() {
    this.authService.logout()
  }


}
