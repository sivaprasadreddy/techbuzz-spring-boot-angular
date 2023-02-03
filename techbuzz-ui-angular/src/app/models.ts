export interface Category {
  id: number
  name: string
  slug: string
  description: string
  image: string
  displayOrder: number
}

export interface User {
  id: number
  name: string
  email: string
  role: string
  verified: string
}

export interface Vote {
  id: number
  userId: number
  postId: number
  value: number
}

export interface Post {
  id: number
  title: string
  url: string
  content: string
  category: Category
  createdBy: User
  votes: Vote[]
  createdAt: Date
}

export interface PostUserView {
  id: number
  title: string
  url: string
  content: string
  category: Category
  createdBy: User
  votes: Vote[]
  createdAt: Date
  editable: boolean
  upVoted: boolean
  downVoted: boolean
  upVoteCount: number
  downVoteCount: number
}

export interface CategoryView {
  id: number
  name: string
  slug: string
  description: string
  image: string
  posts: PostsResponse
}

export interface PostsResponse {
  data: PostUserView[],
  totalElements: number
  pageNumber: number
  totalPages: number
  isFirst: boolean
  isLast: boolean
  hasNext: boolean
  hasPrevious: boolean
}

export interface CreatePostRequest {
  title: string
  url: string
  content: string
  categoryId: number
}

export interface UpdatePostRequest {
  id: number
  title: string
  url: string
  content: string
  categoryId: number
}

export interface CreateCommentRequest {
  username: string
  content: string
}

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
