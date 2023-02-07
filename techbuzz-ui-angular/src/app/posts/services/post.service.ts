import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Category, CategoryView, CreatePostRequest, Post, UpdatePostRequest, Vote} from "../models/models";
import { environment } from "../../../environments/environment"

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiBaseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  getCategories() {
    return this.http.get<Category[]>(`${this.apiBaseUrl + '/api/categories'}`);
  }

  getCategory(slug: string, page: number) {
    return this.http.get<CategoryView>(`${this.apiBaseUrl}/api/categories/${slug}?page=${page}`);
  }

  upVote(postId: number) {
    return this.http.post<Vote>(`${this.apiBaseUrl + '/api/posts/'+ postId + '/votes'}`, {value: 1})
  }

  downVote(postId: number) {
    return this.http.post<Vote>(`${this.apiBaseUrl + '/api/posts/'+ postId + '/votes'}`, {value: -1})
  }

  getPost(postId: number) {
    return this.http.get<Post>(`${this.apiBaseUrl}/api/posts/${postId}`);
  }

  createPost(createPostRequest: CreatePostRequest) {
    return this.http.post<Post>(`${this.apiBaseUrl + '/api/posts'}`, createPostRequest)
  }
  updatePost(updatePostRequest: UpdatePostRequest) {
    return this.http.put<Post>(`${this.apiBaseUrl + '/api/posts/'+ updatePostRequest.id}`, updatePostRequest)
  }
  deletePost(postId: number) {
    return this.http.delete(`${this.apiBaseUrl + '/api/posts/'+ postId}`)
  }

}
