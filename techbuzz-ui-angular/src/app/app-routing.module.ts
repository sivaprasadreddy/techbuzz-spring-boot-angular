import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: "full"},
  {path: '',  loadChildren: () => import("./auth/auth.module").then((m) => m.AuthModule),},
  {path: '',  loadChildren: () => import("./posts/posts.module").then((m) => m.PostsModule),},
  //Keep shared module last as it has catch all route handler
  {path: '',  loadChildren: () => import("./shared/shared.module").then((m) => m.SharedModule),},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

export const routingComponents = [
]
