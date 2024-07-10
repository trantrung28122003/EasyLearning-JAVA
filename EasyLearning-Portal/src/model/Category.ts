import { Course } from "./Course";

export interface Category {
  id: string;
  categoryName: string;
  courses: Course[];
}
