
export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  access_token: string
  expires_at: string
  name: string
  email: string
  role: string
}

export interface CreateUserRequest {
  email: string
  password: string
  name: string
}
export interface CreateUserResponse {
  email: string
  name: string
}

export interface VerifyEmailResponse {
  success: boolean
}
