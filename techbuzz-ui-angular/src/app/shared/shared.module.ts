import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {routingComponents, SharedRoutingModule} from "./shared-routing.module";

@NgModule({
  declarations: [
    routingComponents
  ],
  imports: [
    CommonModule,
    SharedRoutingModule,
  ],
  providers: [
  ],
  exports: [
    routingComponents
  ]
})
export class SharedModule { }
