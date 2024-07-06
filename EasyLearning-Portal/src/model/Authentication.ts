export interface LoginRequest {
  userName: string;
  password: string;
}

export interface LoginResponse {
  authenticated: boolean;
  token: string;
}

export interface RegisterRequest {
  userName: string;
  email: string;
  fullName: string;
  dayOfBirth: Date | null;
  imageUrl: File | null;
  password: string;
}
