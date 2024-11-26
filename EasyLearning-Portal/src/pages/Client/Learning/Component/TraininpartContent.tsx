import React, { useState, useRef, useEffect } from "react";
import { TrainingPartProgressResponses } from "../../../../model/Course";
import video from "../CaiDatMoiTruong.mp4";
import NoteBox from "./Note";
import ExerciseQuiz from "../QuizApp/ExerciseQuiz";

interface ScoreRequest {
  correctAnswersCount: number;
  totalQuestionsCount: number;
}

interface TrainingPartContentProps {
  trainingPart: TrainingPartProgressResponses;
  courseInstructor: string;
  onVideoCompleted: (trainingPartId: string) => Promise<void>;
  onQuizCompleted: (
    trainingPartId: string,
    scoreRequest: ScoreRequest
  ) => Promise<void>;
}

const TrainingPartContent: React.FC<TrainingPartContentProps> = ({
  trainingPart,
  courseInstructor,
  onVideoCompleted,
  onQuizCompleted,
}) => {
  const [progress, setProgress] = useState(0);
  const videoRef = useRef<HTMLVideoElement>(null);
  const [hasCompleted, setHasCompleted] = useState(false);
  const [currentTime, setCurrentTime] = useState(0);
  const [isVideoWatched, setIsVideoWatched] = useState(false);
  const handleQuizCompleted = (
    trainingPartId: string,
    scoreRequest: ScoreRequest
  ) => {
    onQuizCompleted(trainingPartId, scoreRequest);
  };
  const handleTimeUpdate = () => {
    if (videoRef.current) {
      const currentTime = videoRef.current.currentTime;
      const duration = videoRef.current.duration;
      const percentWatched = (currentTime / duration) * 100;
      setProgress(percentWatched);
      setCurrentTime(currentTime);
      if (percentWatched >= 90 && !hasCompleted && !trainingPart.completed) {
        console.log("Tao goi api ne con");
        onVideoCompleted(trainingPart.id);
        setHasCompleted(true);
      }
    }
  };

  const formatTime = (timeInSeconds: number) => {
    const hours = Math.floor(timeInSeconds / 3600);
    const minutes = Math.floor((timeInSeconds % 3600) / 60);
    const seconds = Math.floor(timeInSeconds % 60);
    if (hours > 0) {
      return `${hours.toString().padStart(2, "0")}:${minutes
        .toString()
        .padStart(2, "0")}:${seconds.toString().padStart(2, "0")}`;
    } else {
      return `${minutes.toString().padStart(2, "0")}:${seconds
        .toString()
        .padStart(2, "0")}`;
    }
  };

  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.load();
    }
    setHasCompleted(false);
  }, [trainingPart.id]);

  return (
    <div className="video-player">
      {trainingPart.trainingPartType === "LESSON" ? (
        <div className="video">
          <video
            ref={videoRef}
            controls
            controlsList="nodownload"
            style={{ border: "1px solid ccc", borderRadius: "5px" }}
            onTimeUpdate={handleTimeUpdate} // Gọi hàm khi video cập nhật thời gian
          >
            <source src={trainingPart.videoUrl} type="video/mp4" />
            Trình duyệt của bạn không hỗ trợ phát video.
          </video>
        </div>
      ) : (
        <ExerciseQuiz
          trainingPartId={trainingPart.id}
          isComplete={trainingPart.completed}
          onQuizCompleted={handleQuizCompleted}
        />
      )}
      <div className="video-info">
        <h2 style={{ maxWidth: "40rem" }}>{trainingPart.trainingPartName}</h2>
        <div className="video-details">
          <div className="rating">
            <strong>⭐ 4.5</strong>
            <span>72,600 đánh giá</span>
          </div>
          <div className="students">
            <strong>👥 728,533</strong>
            <span> Người tham gia</span>
          </div>
          <div className="duration">
            <strong>⏱ 9.5 hours</strong>
            <span> Tổng giờ</span>
          </div>
        </div>
        <p className="last-updated">
          <i className="fas fa-exclamation-circle"></i> {""}Cập nhập gần đây
          nhất: {new Date(trainingPart.dateChange).toLocaleDateString("vi-VN")}{" "}
          | Ngôn Ngữ: Tiếng việt
        </p>
        <div className="course-stats">
          <span>
            Người hướng dẫn: <strong> {courseInstructor}</strong>
          </span>{" "}
          |{" "}
          <span>
            Bài học:{" "}
            <strong>
              {trainingPart.trainingPartType == "LESSON"
                ? "Lý thuyết"
                : "Bài Tập"}
            </strong>
          </span>
        </div>
        <div className="note">
          <button className="btn-note">
            <i className="fas fa-plus"></i>{" "}
            <span style={{ marginLeft: "12px" }}>Thêm ghi chú tại </span>
            <strong>{formatTime(currentTime)}</strong>
          </button>
        </div>
      </div>
    </div>
  );
};

export default TrainingPartContent;
