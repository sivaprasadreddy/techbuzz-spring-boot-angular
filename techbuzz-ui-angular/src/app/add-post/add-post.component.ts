import {Component, OnInit} from '@angular/core';
import {PostService} from "../post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";
import {Category, CreatePostRequest} from "../models";

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit{
  categories : Category[] = []

  addPostForm = this.fb.group({
    title: ['', [Validators.required, Validators.pattern(/\S/)]],
    url: ['', [Validators.required, Validators.pattern(/\S/)]],
    content: ['', [Validators.required, Validators.pattern(/\S/)]],
    categoryId: [1, [Validators.required]]
  });

  constructor(private postService: PostService,
              private router: Router,
              private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.postService.getCategories().subscribe(response => {
      console.log(response)
      this.categories = response;
    })
  }

  onAddPostSubmit() {
    if(this.addPostForm.invalid) {
      return;
    }
    const createPostRequest : CreatePostRequest = {
      title: this.addPostForm.value.title || "",
      url: this.addPostForm.value.url || "",
      categoryId: this.addPostForm.value.categoryId || 1,
      content: this.addPostForm.value.content || ""
    }
    this.postService.createPost(createPostRequest).subscribe(postResp => {
      console.log(postResp);
      this.router.navigate(['/categories/'+postResp.category.slug])
    })
  }
}
