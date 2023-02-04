import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from "../shared/guards/auth.guard";
import {HomeComponent} from "./pages/home/home.component";
import {CategoryComponent} from "./pages/category/category.component";
import {AddPostComponent} from "./pages/add-post/add-post.component";
import {EditPostComponent} from "./pages/edit-post/edit-post.component";
import {PaginationComponent} from "./components/pagination/pagination.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'categories/:slug', component: CategoryComponent},
  {path: 'posts/new', component: AddPostComponent, canActivate: [AuthGuard]},
  {path: 'posts/:id/edit', component: EditPostComponent, canActivate: [AuthGuard]},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostsRoutingModule {
}

export const routingComponents = [
  PaginationComponent,
  AddPostComponent,
  HomeComponent,
  CategoryComponent,
  EditPostComponent,
]
