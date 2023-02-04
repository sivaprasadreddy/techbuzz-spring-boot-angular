import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Category, CategoryView} from "../../models/models";
import {PostService} from "../../services/post.service";
import {AuthService} from "../../../auth/services/auth.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  slug = ""
  page = 1
  categories : Category[] = []
  category: CategoryView = {posts: {
      data: [],
      totalElements: 0,
      pageNumber: 0,
      totalPages: 0,
      isFirst: false,
      isLast: false,
      hasNext: false,
      hasPrevious: false
    }, description: "", id: 0, image: "", name: "", slug: ""}

  constructor(private route: ActivatedRoute, private postService: PostService,private authService: AuthService) {
  }

  ngOnInit(): void {
    this.postService.getCategories().subscribe(response => {
      console.log(response)
      this.categories = response;
    })
    this.route.queryParamMap.subscribe(params => {
      this.page = parseInt(params.get('page') || "1");
      this.fetchCategory()
    })
    this.route.paramMap
      .subscribe(params => {
          this.slug = params.get('slug') || "";
          this.fetchCategory()
        }
      );
  }

  fetchCategory() {
    if(!this.slug) {
      return;
    }
    this.postService.getCategory(this.slug, this.page).subscribe(response => {
      this.category = response;
    })
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
  upVote(postId: number) {
    return this.postService.upVote(postId).subscribe(response => {
      this.fetchCategory()
    });
  }

  downVote(postId: number) {
    return this.postService.downVote(postId).subscribe(response => {
      this.fetchCategory()
    });
  }

  deletePost(postId: number) {
    return this.postService.deletePost(postId).subscribe(response => {
      this.fetchCategory()
    });
  }
}
