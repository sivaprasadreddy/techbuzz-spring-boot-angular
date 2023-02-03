import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-verify-email',
  templateUrl: './verify-email.component.html',
  styleUrls: ['./verify-email.component.css']
})
export class VerifyEmailComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private router: Router,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      let email = params.get('email') || "";
      let token = params.get('token') || "";
      let success = "failure";
      if ( email && token ) {
          this.authService.verifyEmail(email, token).subscribe(response => {
            success = response.success ? "success": "failure";
            this.router.navigateByUrl("/verify-email-status?status="+success)
          })
      } else {
        this.router.navigateByUrl("/verify-email-status?status="+success)
      }
    })
  }
}
