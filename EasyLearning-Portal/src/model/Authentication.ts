export interface LoginRequest {
  userName: string;
  password: string;
}

export interface LoginResponse {
  authenticated: boolean;
  token: string;
}

export interface APIRegisterRequest {
  userName: string;
  email: string;
  fullName: string;
  dayOfBirth: Date | null;
  imageName: string | null;
  imageUrl: File | null;
  password: string;
}

export interface RegisterRequest extends APIRegisterRequest {
  confirmPassword: string;
  termAndConditions: boolean;
}
