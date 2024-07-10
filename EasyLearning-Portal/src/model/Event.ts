import { TrainingPart } from "./Course";

export interface EventSlim {
  id: string;
  courseEventName: string;
  startTime: string;
  endTime: string;
  totalTrainingPartByCourseEvent: number;
  trainingParts: TrainingPart[];
}
