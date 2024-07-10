export interface Feedback {
  id: string;
  feedbackUserId: string;
  feedbackContent: string;
  feedbackRating: number;
  dateCreate: string;
  dateChange: string;
  changedBy: string;
  courseId: string;
  deleted: boolean;
}
