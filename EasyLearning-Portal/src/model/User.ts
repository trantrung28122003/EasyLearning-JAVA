export interface User {
  id: string;
  userName: string;
  email: string;
  fullName: string;
  dayOfBirth: any;
  imageUrl: any;
  roles: Role[];
  dateCreate: string;
  dateChange: string;
  changedBy: string;
  deleted: boolean;
}

export interface Role {
  id: string;
  name: string;
}
