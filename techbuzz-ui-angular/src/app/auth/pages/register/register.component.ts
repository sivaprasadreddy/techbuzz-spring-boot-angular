import { Component } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router) {
  }
  error : string | null = null;
  registrationForm = this.fb.group({
    email: ['', [Validators.required, Validators.pattern(/\S/)]],
    password: ['', [Validators.required, Validators.pattern(/\S/)]],
    name: ['', [Validators.required, Validators.pattern(/\S/)]],
  });

  register() {
    this.authService.register({
      email: this.registrationForm.value.email ||"",
      password: this.registrationForm.value.password ||"",
      name: this.registrationForm.value.password ||"",
    }).subscribe({
      next: response => {
        console.log("registration response:", response)
        this.router.navigate(['/login'])
      },
      error: () => {
        this.error = "Registration failed"
      }
    });
  }
}
