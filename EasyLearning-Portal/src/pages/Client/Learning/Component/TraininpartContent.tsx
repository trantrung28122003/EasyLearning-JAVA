import React, { useState, useRef, useEffect } from "react";
import { TrainingPartProgressResponses } from "../../../../model/Course";
import ExerciseQuiz from "../QuizApp/ExerciseQuiz";
import Comments from "./Comment";

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
  onWarningMessage: (message: string) => Promise<void>;
}

const TrainingPartContent: React.FC<TrainingPartContentProps> = ({
  trainingPart,
  courseInstructor,
  onVideoCompleted,
  onQuizCompleted,
  onWarningMessage,
}) => {
  const videoRef = useRef<HTMLVideoElement>(null);
  const [hasCompleted, setHasCompleted] = useState(false);
  const [currentTime, setCurrentTime] = useState(0);
  const [isCommentBox, setIsCommentBox] = useState(false);
  const [actualWatchTime, setActualWatchTime] = useState(0);
  const [previousTime, setPreviousTime] = useState(0);
  const [skipCount, setSkipCount] = useState(0);

  const skipLimit = 10;

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
      setCurrentTime(currentTime);
      if (percentWatched >= 90 && !hasCompleted && !trainingPart.completed) {
        console.log("Tao goi api ne con");
        onVideoCompleted(trainingPart.id);
        setHasCompleted(true);
      }
      setPreviousTime(currentTime);
      if (Math.floor(currentTime) > actualWatchTime) {
        setActualWatchTime((prevTime) => prevTime + 1);
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

  const toggleCommentBox = () => {
    setIsCommentBox(!isCommentBox);
  };
  const handleSeeking = () => {
    if (videoRef.current != null) {
      const currentTime = videoRef.current.currentTime;
      const duration = videoRef.current.duration;
      setCurrentTime(currentTime);
      const maxSkipTime = (duration * 30) / 100;
      if (currentTime > maxSkipTime && actualWatchTime + 20 < currentTime) {
        onWarningMessage(
          "Bạn đã tua quá nhanh! Video sẽ quay lại vị trí trước đó."
        );
        videoRef.current.currentTime = previousTime;
        videoRef.current.pause();
      } else if (currentTime > previousTime) {
        setSkipCount((prevCount) => prevCount + 1);
        if (skipCount + 1 >= skipLimit) {
          onWarningMessage(
            "Bạn đang tua quá nhiều lần! Hãy xem video nghiêm túc."
          );

          videoRef.current.currentTime = previousTime;
          videoRef.current.pause();
          setSkipCount(0);
        }
      }
    }
  };
  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.load();
    }
    setHasCompleted(trainingPart.completed);
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
            onTimeUpdate={handleTimeUpdate}
            onSeeking={handleSeeking}
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
        <div className="note">
          <button className="btn-comment" onClick={toggleCommentBox}>
            <i className="fas fa-comment"></i>
            <span style={{ marginLeft: "3px" }}>Hỏi đáp</span>
          </button>
        </div>

        {isCommentBox && (
          <div className="wrapper-note">
            <div className="over-lay" onClick={toggleCommentBox}>
              {" "}
            </div>
            <div className="comment-box">
              <div className="note-header d-flex justify-content-between align-items-center p-3">
                <h5 className="mb-0">Hỏi đáp về phần học</h5>
                <button
                  type="button"
                  className="close-notes btn btn-outline-primary btn-rounded"
                  onClick={toggleCommentBox}
                >
                  Đóng cửa sổ
                </button>
              </div>
              <Comments trainingPartId={trainingPart.id} />
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default TrainingPartContent;
