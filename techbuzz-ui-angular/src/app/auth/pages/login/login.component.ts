import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router) {
  }
  error : string | null = null;
  loginForm = this.fb.group({
    username: ['', [Validators.required, Validators.pattern(/\S/)]],
    password: ['', [Validators.required, Validators.pattern(/\S/)]]
  });

  login() {
    this.authService.login({
      username: this.loginForm.value.username ||"",
      password: this.loginForm.value.password ||"",
    }).subscribe({
      next: response => {
          console.log("login response:", response)
          this.authService.setAuthUser(response);
          this.router.navigate(['/'])
      },
      error: () => {
        this.error = "Invalid credentials"
      }
    });
  }
}
