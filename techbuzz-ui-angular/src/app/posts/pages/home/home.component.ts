import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Category} from "../../models/models";
import {PostService} from "../../services/post.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  categories : Category[] = []

  constructor(private route: ActivatedRoute, private postService: PostService) {
  }

  ngOnInit(): void {
          this.postService.getCategories().subscribe(response => {
            console.log(response)
            this.categories = response;
          })

  }
}
