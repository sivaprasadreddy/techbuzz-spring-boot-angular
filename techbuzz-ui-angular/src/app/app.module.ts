import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import {AuthGuard} from "./auth.guard";
import {HttpInterceptorService} from "./http-interceptor.service";
import { VerifyEmailComponent } from './verify-email/verify-email.component';
import { VerifyEmailStatusComponent } from './verify-email-status/verify-email-status.component';

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    VerifyEmailComponent,
    VerifyEmailStatusComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
