import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";
import {Category, UpdatePostRequest} from "../../models/models";
import {PostService} from "../../services/post.service";

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {
  categories : Category[] = []

  editPostForm = this.fb.group({
    id: [0, [Validators.required]],
    title: ['', [Validators.required, Validators.pattern(/\S/)]],
    url: ['', [Validators.required, Validators.pattern(/\S/)]],
    content: ['', [Validators.required, Validators.pattern(/\S/)]],
    categoryId: [1, [Validators.required]]
  });

  constructor(private postService: PostService,
              private route: ActivatedRoute,
              private router: Router,
              private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.postService.getCategories().subscribe(response => {
      console.log(response)
      this.categories = response;
    })

    this.route.paramMap
      .subscribe(params => {
          let postId = parseInt(params.get('id') || "");
          this.postService.getPost(postId).subscribe(response => {

            this.editPostForm.setValue({
              title: response.title,
              url: response.url,
              id: response.id,
              content: response.content,
              categoryId: response.category.id
            })
          })
        }
      );
  }

  onEditPostSubmit() {
    if(this.editPostForm.invalid) {
      return;
    }
    const updatePostRequest : UpdatePostRequest = {
      id: this.editPostForm.value.id || 0,
      title: this.editPostForm.value.title || "",
      url: this.editPostForm.value.url || "",
      categoryId: this.editPostForm.value.categoryId || 1,
      content: this.editPostForm.value.content || ""
    }
    this.postService.updatePost(updatePostRequest).subscribe(postResp => {
      console.log(postResp);
      this.router.navigate(['/categories/'+postResp.category.slug])
    })
  }
}
