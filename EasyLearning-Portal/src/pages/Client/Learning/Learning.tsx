import React, { useEffect, useState } from "react";
import "./Learning.css";
import {
  CREATE_CERTIFICATE,
  GET_TRAINING_PROGRESS_STATUS,
  UPDATE_TRAINING_PROGRESS,
} from "../../../constants/API";
import { DoCallAPIWithToken } from "../../../services/HttpService";
import { HTTP_OK } from "../../../constants/HTTPCode";
import { useNavigate, useParams } from "react-router-dom";
import {
  UserTrainingProgressStatusResponse,
  TrainingPartProgressResponses,
} from "../../../model/Course";
import TrainingPartContent from "./Component/TraininpartContent";
import { EventSlim } from "../../../model/Event";

import Note from "./Component/Note";
interface ScoreRequest {
  correctAnswersCount: number;
  totalQuestionsCount: number;
}

const Learning: React.FC = () => {
  const [isLoading, setIsLoading] = useState(false);
  const { courseId } = useParams<{ courseId: string }>();
  const navigate = useNavigate();
  const [openEvents, setOpenEvents] = useState<{ [key: string]: boolean }>({});
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [isNoteBox, setIsNoteBox] = useState(false);
  const [selectedTrainingPart, setSelectedTrainingPart] =
    useState<TrainingPartProgressResponses | null>(null);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  const toggleNoteBox = () => {
    setIsNoteBox(!isNoteBox);
  };

  const toggleEventDetails = (eventName: string) => {
    setOpenEvents((prev) => ({
      ...prev,
      [eventName]: !prev[eventName],
    }));
  };

  const selectTrainingPart = (part: TrainingPartProgressResponses) => {
    setSelectedTrainingPart(part);
  };

  const calculateProgressPercentage = (
    completedLessons: number,
    totalLessons: number
  ) => {
    return Math.round((completedLessons / totalLessons) * 100);
  };

  const [trainingProgressData, setTrainingProgressData] =
    useState<UserTrainingProgressStatusResponse | null>(null);

  const FetchUserTrainingProgress = async (courseId: string) => {
    setIsLoading(true);
    try {
      const URL = GET_TRAINING_PROGRESS_STATUS + "/" + courseId;
      const response = await DoCallAPIWithToken(URL, "GET");
      if (response.status === HTTP_OK) {
        const data = await response.data.result;
        setTrainingProgressData(data);

        const trainingParts = data.courseEventsResponses.flatMap(
          (event: EventSlim) => event.trainingPartProgressResponses
        );
        const completedParts = trainingParts.filter(
          (part: TrainingPartProgressResponses) => part.completed === true
        );

        const lastCompletedIndex = trainingParts.indexOf(
          completedParts[completedParts.length - 1]
        );
        const nextTrainingPart = trainingParts[lastCompletedIndex + 1];

        if (nextTrainingPart) {
          setSelectedTrainingPart(nextTrainingPart);
        } else {
          const firstTrainingPart = data.courseEventsResponses
            .flatMap((event: EventSlim) => event.trainingPartProgressResponses)
            .find((part: TrainingPartProgressResponses) => part);

          if (firstTrainingPart) {
            setSelectedTrainingPart(firstTrainingPart);
          }
        }
      }
    } catch (error) {
    } finally {
      setIsLoading(false);
    }
  };

  const handleVideoCompleted = async (trainingPartId: string) => {
    try {
      const URL = UPDATE_TRAINING_PROGRESS + "/" + trainingPartId;
      const response = await DoCallAPIWithToken(URL, "POST");
      if (response.status === HTTP_OK) {
        if (courseId) {
          FetchUserTrainingProgress(courseId);
          console.log(`Phần học ${trainingPartId} đã hoàn thành`);
        }
      }
    } catch (error) {
      console.error("Không thể cập nhật trạng thái hoàn thành:", error);
    }
  };

  const handleQuizCompleted = async (
    trainingPartId: string,
    scoreRequest: ScoreRequest
  ) => {
    try {
      const URL = UPDATE_TRAINING_PROGRESS + "/" + trainingPartId;
      const response = await DoCallAPIWithToken(URL, "POST", scoreRequest);
      if (response.status === HTTP_OK) {
        if (courseId) {
          console.log("score", scoreRequest);
          FetchUserTrainingProgress(courseId);
          console.log(`Phần học ${trainingPartId} đã hoàn thành`);
        }
      }
    } catch (error) {
      console.error("Không thể cập nhật trạng thái hoàn thành:", error);
    }
  };

  const goToNextPart = () => {
    if (!selectedTrainingPart || !trainingProgressData) return;

    const flatTrainingParts =
      trainingProgressData.courseEventsResponses.flatMap(
        (event) => event.trainingPartProgressResponses
      );

    const currentIndex = flatTrainingParts.indexOf(selectedTrainingPart);
    if (
      currentIndex !== -1 &&
      currentIndex < flatTrainingParts.length - 1 &&
      selectedTrainingPart.completed
    ) {
      setSelectedTrainingPart(flatTrainingParts[currentIndex + 1]);
    }
  };

  const goToPreviousPart = () => {
    if (!selectedTrainingPart || !trainingProgressData) return;

    const flatTrainingParts =
      trainingProgressData.courseEventsResponses.flatMap(
        (event) => event.trainingPartProgressResponses
      );

    const currentIndex = flatTrainingParts.indexOf(selectedTrainingPart);
    if (currentIndex > 0) {
      setSelectedTrainingPart(flatTrainingParts[currentIndex - 1]);
    }
  };

  const isLastTrainingPart = (): boolean => {
    if (!selectedTrainingPart || !trainingProgressData) return false;
    const flatTrainingParts =
      trainingProgressData.courseEventsResponses.flatMap(
        (event) => event.trainingPartProgressResponses
      );
    return (
      selectedTrainingPart === flatTrainingParts[flatTrainingParts.length - 1]
    );
  };

  const fetchCreateCertificate = async () => {
    setIsLoading(true);
    try {
      const URL = CREATE_CERTIFICATE + `?courseId=${courseId}`;
      const response = await DoCallAPIWithToken(URL, "POST");
      if (response.status === HTTP_OK) {
        if (courseId) {
          console.log("tao chung chi thanh cong ne conn");
          FetchUserTrainingProgress(courseId);
        }
      }
    } catch (error) {
    } finally {
      setIsLoading(false);
    }
  };

  const completeCourse = async () => {
    if (selectedTrainingPart?.id && !selectedTrainingPart.completed) {
      try {
        const URL = UPDATE_TRAINING_PROGRESS + "/" + selectedTrainingPart.id;
        const response = await DoCallAPIWithToken(URL, "POST");
        if (response.status === HTTP_OK) {
          if (courseId) {
            FetchUserTrainingProgress(courseId);
          }
        }
      } catch (error) {
        console.error("Không thể cập nhật trạng thái hoàn thành:", error);
      }
      await fetchCreateCertificate();
      navigate("/confirmCertificate", { state: { courseId } });
    } else {
      navigate("/certificate");
    }
  };

  const canSelectTrainingPart = (
    part: TrainingPartProgressResponses
  ): boolean => {
    return part.completed;
  };

  const isNextPart = (part: TrainingPartProgressResponses): boolean => {
    const lastCompletedPart = trainingProgressData?.courseEventsResponses
      .flatMap((event: EventSlim) => event.trainingPartProgressResponses)
      .reverse()
      .find((part: TrainingPartProgressResponses) => part.completed === true);
    const firstTrainingPart =
      trainingProgressData?.courseEventsResponses.flatMap(
        (event: EventSlim) => event.trainingPartProgressResponses
      )[0];
    if (!lastCompletedPart && part === firstTrainingPart) return true;
    if (!lastCompletedPart && part) return false;

    const flatTrainingParts =
      trainingProgressData?.courseEventsResponses.flatMap(
        (event) => event.trainingPartProgressResponses
      );

    if (!flatTrainingParts) return false;

    const lastCompletedIndex = flatTrainingParts.findIndex(
      (part: TrainingPartProgressResponses) => part === lastCompletedPart
    );

    if (lastCompletedIndex === -1) return false;

    return lastCompletedIndex + 1 === flatTrainingParts.indexOf(part);
  };

  useEffect(() => {
    if (courseId) {
      FetchUserTrainingProgress(courseId);
    }
  }, [courseId]);

  return (
    <div className="app">
      {isLoading && (
        <div className="loading-overlay">
          <div className="spinner"></div>
        </div>
      )}
      <div className="header">
        <div className="logo">eLEARNING</div>
        <div className="vertical-divider"></div>
        <div className="course-info">
          <span className="course-title">
            {trainingProgressData?.courseName}
          </span>
        </div>
        <div className="course-progress">
          <div
            className="circular-progress"
            style={{
              backgroundImage: `conic-gradient(
                                          #38c9d6 ${calculateProgressPercentage(
                                            trainingProgressData?.completedPartsByCourse ??
                                              0,
                                            trainingProgressData?.totalPartsByCourse ??
                                              0
                                          )}%,
                                          #808080 ${
                                            calculateProgressPercentage(
                                              trainingProgressData?.completedPartsByCourse ??
                                                0,
                                              trainingProgressData?.totalPartsByCourse ??
                                                0
                                            ) || 0
                                          }%
                                        )`,
            }}
          >
            <span className="progress-value">
              {calculateProgressPercentage(
                trainingProgressData?.completedPartsByCourse ?? 0,
                trainingProgressData?.totalPartsByCourse ?? 0
              ) || 0}{" "}
              %
            </span>
          </div>
          <span>
            {trainingProgressData?.completedPartsByCourse ?? 0}/
            {trainingProgressData?.totalPartsByCourse ?? 0} bài học
          </span>
        </div>
        <div className="header-actions">
          <button className="action-btn" onClick={toggleNoteBox}>
            Ghi chú <i className="far fa-sticky-note"></i>
          </button>
          <button className="menu-btn">
            <i className="fas fa-ellipsis-h"></i>
          </button>
          <button
            className="menu-btn"
            onClick={() => {
              navigate("/userCourses");
            }}
          >
            <i className="fas fa-sign-out-alt"></i>
          </button>
        </div>
      </div>

      <div className={`main ${isSidebarOpen ? "" : "with-closed-sidebar"}`}>
        <div className={`sidebar ${isSidebarOpen ? "" : "closed"}`}>
          {isSidebarOpen && (
            <>
              <div className="sidebar-header">
                <h4>Nội dung khóa học</h4>
                <button className="btn" onClick={() => setIsSidebarOpen(false)}>
                  <i className="fas fa-times"></i>
                </button>
              </div>
              <div className="tab-content">
                <dl>
                  {trainingProgressData?.courseEventsResponses.map(
                    (classEvent, eventIndex) => (
                      <React.Fragment key={classEvent.courseEventName}>
                        <div
                          className="toggle-details"
                          onClick={() =>
                            toggleEventDetails(classEvent.courseEventName)
                          }
                        >
                          <div className="header-content">
                            <div className="section-title">
                              {`${eventIndex + 1}. ${
                                classEvent.courseEventName
                              }`}
                            </div>
                            <span className="icon">
                              <i
                                className={`fas ${
                                  openEvents[classEvent.courseEventName]
                                    ? "fa-chevron-up"
                                    : "fa-chevron-down"
                                }`}
                              ></i>
                            </span>
                          </div>
                          <span className="section-progress">
                            {
                              classEvent.trainingPartProgressResponses.filter(
                                (part) => part.completed === true
                              ).length
                            }{" "}
                            / {classEvent.trainingPartProgressResponses.length}{" "}
                            | 7 phút
                          </span>
                        </div>
                        {openEvents[classEvent.courseEventName] && (
                          <div className="details">
                            {classEvent.trainingPartProgressResponses.map(
                              (part, partIndex) => (
                                <div
                                  key={partIndex}
                                  className={`training-part-item ${
                                    part === selectedTrainingPart
                                      ? "selected"
                                      : ""
                                  }`}
                                  onClick={
                                    canSelectTrainingPart(part) ||
                                    isNextPart(part)
                                      ? () => selectTrainingPart(part)
                                      : undefined
                                  }
                                  style={{
                                    cursor: canSelectTrainingPart(part)
                                      ? "pointer"
                                      : isNextPart(part)
                                      ? "pointer"
                                      : "not-allowed",
                                    opacity: canSelectTrainingPart(part)
                                      ? 1
                                      : isNextPart(part)
                                      ? 1
                                      : 0.5,
                                  }}
                                >
                                  <span style={{ marginLeft: "1rem" }}>
                                    {`${eventIndex + 1}.${partIndex + 1} ${
                                      part.trainingPartName
                                    }`}
                                    <div className="">
                                      {part.trainingPartType === "LESSON" ? (
                                        <i className="fas fa-circle"></i>
                                      ) : (
                                        <i className="fas fa-pen"></i>
                                      )}

                                      <span
                                        style={{
                                          fontSize: "12px",
                                          marginLeft: "8px",
                                        }}
                                      >
                                        7:28
                                      </span>
                                    </div>
                                  </span>
                                  <span>
                                    {!part.completed && !isNextPart(part) ? (
                                      <i
                                        className="fas fa-lock"
                                        style={{ color: "gray" }}
                                      ></i>
                                    ) : part.completed ? (
                                      <i
                                        className="fas fa-check"
                                        style={{
                                          width: "12px",
                                          height: "12px",
                                          color: "green",
                                        }}
                                      ></i>
                                    ) : null}
                                  </span>
                                </div>
                              )
                            )}
                          </div>
                        )}
                      </React.Fragment>
                    )
                  )}
                </dl>
              </div>
            </>
          )}
        </div>
        <div className="container-content-learning " style={{ flex: "1" }}>
          {selectedTrainingPart && !isLastTrainingPart() ? (
            <TrainingPartContent
              trainingPart={selectedTrainingPart}
              courseInstructor={trainingProgressData?.courseInstructor ?? ""}
              onVideoCompleted={handleVideoCompleted}
              onQuizCompleted={handleQuizCompleted}
            />
          ) : (
            <div className="col-lg-12 ">
              <div className="centered-content">
                <img
                  src="https://easylearning.blob.core.windows.net/images-videos/congratulations.png"
                  style={{ width: "380px" }}
                  alt="No data"
                />
                <h2 className="success-message">
                  🎉 Chúc mừng bạn đã hoàn thành khóa học! 🎉
                </h2>
                <h3> {trainingProgressData?.courseName}</h3>
                <p>
                  Bạn đã sẵn sàng để nhận chứng chỉ? Hãy nhấn nút bên dưới để
                  lấy chứng chỉ của bạn ngay bây giờ.
                </p>
                <button className="explore-course-btn" onClick={completeCourse}>
                  Hoàn thành và nhận chứng chỉ khóa học
                </button>
              </div>
            </div>
          )}
        </div>
      </div>

      {isNoteBox && (
        <div className="wrapper-note">
          <div className="over-lay" id="note-box" onClick={toggleNoteBox}>
            {" "}
          </div>
          <div className="note-box" id="note-box">
            <div className="note-header d-flex justify-content-between align-items-center p-3">
              <h5 className="mb-0">Ghi chú</h5>
              <button
                type="button"
                className="close-notes btn btn-outline-primary btn-rounded"
                onClick={toggleNoteBox}
              >
                Đóng cửa sổ
              </button>
            </div>
            <Note />
          </div>
        </div>
      )}

      <div className="footer-learning">
        <button className="toggle-sidebar-btn" onClick={toggleSidebar}>
          {isSidebarOpen ? (
            <i className="fas fa-arrow-left"></i>
          ) : (
            <i className="fas fa-bars"></i>
          )}
        </button>
        <div></div>
        <div className="footer-center-buttons">
          <button
            className="footer-btn"
            onClick={goToPreviousPart}
            disabled={
              !trainingProgressData ||
              !selectedTrainingPart ||
              trainingProgressData.courseEventsResponses
                .flatMap((event) => event.trainingPartProgressResponses)
                .indexOf(selectedTrainingPart) === 0
            }
          >
            <i className="fas fa-chevron-left"></i> BÀI TRƯỚC
          </button>
          {isLastTrainingPart() ? (
            <button className="footer-btn">
              HOÀN THÀNH <i className="fas fa-chevron-right"></i>
            </button>
          ) : (
            <button
              className="footer-btn"
              onClick={goToNextPart}
              disabled={
                !selectedTrainingPart || !selectedTrainingPart.completed
              }
            >
              BÀI TIẾP THEO <i className="fas fa-chevron-right"></i>
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default Learning;
