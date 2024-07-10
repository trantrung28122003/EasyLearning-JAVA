import { EventSlim } from "./Event";
import { Feedback } from "./FeedBack";

export interface Course {
  id: string;
  courseName: string;
  courseDescription: string;
  coursePrice: number;
  requirements: string;
  courseType: string;
  courseContent: string;
  imageUrl: string;
  instructor: string;
  startDate: string;
  endDate: string;
  registrationDeadline: string;
  maxAttendees: number;
  registeredUsers: number;
  dateCreate: string;
  dateChange: string;
  changedBy: string;
  coursesDetails: CoursesDetail[];
  trainerDetails: any[];
  trainingParts: TrainingPart[];
  shoppingCartItems: any[];
  orderDetails: any[];
  feedbacks: Feedback[];
  addOns: any[];
  deleted: boolean;
}

export interface TrainingPart {
  id: string;
  trainingPartName: string;
  startTime: string;
  endTime: string;
  description?: null | string | string;
  trainingPartType: string;
  imageUrl?: any;
  videoUrl?: (null | string)[];
  courseId: string;
  courseEventId?: string | string;
  dateCreate?: string | string;
  dateChange: string;
  changedBy: string;
  free: boolean;
  deleted: boolean;
}
export interface CoursesDetail {
  id: string;
  courseId: string;
  categoryId: string;
  dateCreate: string;
  dateChange?: string;
  changedBy: string;
  deleted: boolean;
}

export interface CourseSlim {
  courseId: string;
  courseName: string;
  courseImage: string;
  nameInstructor: string;
  coursePrice: number;
  totalFeedback: number;
  averageRating: number;
  courseEventResponses: EventSlim[];
}
