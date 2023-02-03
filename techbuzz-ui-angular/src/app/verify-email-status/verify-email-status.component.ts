import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-verify-email-status',
  templateUrl: './verify-email-status.component.html',
  styleUrls: ['./verify-email-status.component.css']
})
export class VerifyEmailStatusComponent implements OnInit {
  success : boolean | null = null

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      let status = params.get('status') || "";
      this.success = status === "success";
    })
  }
}
