import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NavbarComponent} from "./navbar/navbar.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {AddPostComponent} from "./add-post/add-post.component";
import {PaginationComponent} from "./pagination/pagination.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {AuthGuard} from "./auth.guard";
import {HomeComponent} from "./home/home.component";
import {CategoryComponent} from "./category/category.component";
import {EditPostComponent} from "./edit-post/edit-post.component";
import {VerifyEmailComponent} from "./verify-email/verify-email.component";
import {VerifyEmailStatusComponent} from "./verify-email-status/verify-email-status.component";

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: "full"},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'verify-email', component: VerifyEmailComponent},
  {path: 'verify-email-status', component: VerifyEmailStatusComponent},
  {path: 'home', component: HomeComponent},
  {path: 'categories/:slug', component: CategoryComponent},
  {path: 'posts/new', component: AddPostComponent, canActivate: [AuthGuard]},
  {path: 'posts/:id/edit', component: EditPostComponent, canActivate: [AuthGuard]},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

export const routingComponents = [
  NavbarComponent,
  PageNotFoundComponent,
  PaginationComponent,
  AddPostComponent,
  LoginComponent,
  RegisterComponent,
  HomeComponent,
  CategoryComponent,
  EditPostComponent,
  VerifyEmailComponent,
  VerifyEmailStatusComponent,
]
