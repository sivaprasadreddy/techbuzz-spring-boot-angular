import { Injectable } from '@angular/core';
import {CreateUserRequest, CreateUserResponse, LoginRequest, LoginResponse, VerifyEmailResponse} from "../models/models";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import { environment } from "../../../environments/environment"

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiBaseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiBaseUrl}/login`, credentials);
  }

  register(request: CreateUserRequest): Observable<CreateUserResponse> {
    return this.http.post<CreateUserResponse>(`${this.apiBaseUrl}/api/users`, request);
  }

  verifyEmail(email: string, token: string): Observable<VerifyEmailResponse> {
    return this.http.get<VerifyEmailResponse>(`${this.apiBaseUrl}/api/verify-email?email=${email}&token=${token}`);
  }
   setAuthUser(loginResponse: LoginResponse) {
    //console.log("auth resp:", loginResponse)
    localStorage.setItem("token", loginResponse.access_token)
    localStorage.setItem("auth", JSON.stringify(loginResponse))
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem("token")
  }

  loginUserName(): string {
    let auth = localStorage.getItem("auth")
    if(auth) {
      let authJson = JSON.parse(auth) as Auth
      return authJson.name
    }
    return ""
  }

  logout() {
    localStorage.removeItem("token")
  }

  getAuthToken() : string | null {
    return localStorage.getItem("token")
  }
}

interface Auth {
  access_token: string
  email: string
  name: string
  role: string
}
