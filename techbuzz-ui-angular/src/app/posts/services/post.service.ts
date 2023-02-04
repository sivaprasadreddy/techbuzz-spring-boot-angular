import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Category, CategoryView, CreatePostRequest, Post, UpdatePostRequest, Vote} from "../models/models";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private baseApiUrl = "http://localhost:8080"
  constructor(private http: HttpClient) { }

  getCategories() {
    return this.http.get<Category[]>(`${this.baseApiUrl + '/api/categories'}`);
  }

  getCategory(slug: string, page: number) {
    return this.http.get<CategoryView>(`${this.baseApiUrl}/api/categories/${slug}?page=${page}`);
  }

  upVote(postId: number) {
    return this.http.post<Vote>(`${this.baseApiUrl + '/api/posts/'+ postId + '/votes'}`, {value: 1})
  }

  downVote(postId: number) {
    return this.http.post<Vote>(`${this.baseApiUrl + '/api/posts/'+ postId + '/votes'}`, {value: -1})
  }

  getPost(postId: number) {
    return this.http.get<Post>(`${this.baseApiUrl}/api/posts/${postId}`);
  }

  createPost(createPostRequest: CreatePostRequest) {
    return this.http.post<Post>(`${this.baseApiUrl + '/api/posts'}`, createPostRequest)
  }
  updatePost(updatePostRequest: UpdatePostRequest) {
    return this.http.put<Post>(`${this.baseApiUrl + '/api/posts/'+ updatePostRequest.id}`, updatePostRequest)
  }
  deletePost(postId: number) {
    return this.http.delete(`${this.baseApiUrl + '/api/posts/'+ postId}`)
  }

}
