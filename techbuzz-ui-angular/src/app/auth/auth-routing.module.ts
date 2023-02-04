import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {VerifyEmailComponent} from "./pages/verify-email/verify-email.component";
import {VerifyEmailStatusComponent} from "./pages/verify-email-status/verify-email-status.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'verify-email', component: VerifyEmailComponent},
  {path: 'verify-email-status', component: VerifyEmailStatusComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {
}

export const routingComponents = [
  LoginComponent,
  RegisterComponent,
  VerifyEmailComponent,
  VerifyEmailStatusComponent,
]
