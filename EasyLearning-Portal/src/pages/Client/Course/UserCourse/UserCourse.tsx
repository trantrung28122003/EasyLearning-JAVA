import React, { useEffect, useState } from "react";
import {
  GET_COMPLETED_PARTS_BY_COURSE,
  GET_COURSE_BY_USER,
} from "../../../../constants/API";
import { ApplicationResponse } from "../../../../model/BaseResponse";
import { CourseSlim } from "../../../../model/Course";
import { DoCallAPIWithToken } from "../../../../services/HttpService";
import ClientShared from "../../Shared/ClientShared";
import "./UserCourse.css";
import { useNavigate } from "react-router-dom";
const UserCourse: React.FC = () => {
  const [userCourse, getUserCourse] = useState<CourseSlim[]>([]);

  const navigate = useNavigate();
  const doCallGetCourseByUser = () => {
    DoCallAPIWithToken(GET_COURSE_BY_USER, "get").then((res) => {
      const response: ApplicationResponse<CourseSlim[]> = res.data;
      console.log(response.result);
      getUserCourse(response.result);
    });
  };

  const caculateProgressPercentage = (
    completedLessons: number,
    totalLessons: number
  ) => {
    return Math.round((completedLessons / totalLessons) * 100);
  };
  const [courseProgress, setCourseProgress] = useState<Record<string, number>>(
    {}
  );

  useEffect(() => {
    doCallGetCourseByUser();
  }, []);
  return (
    <ClientShared>
      <div className="page-content-user-course">
        {userCourse.length === 0 ? (
          <div className="col-lg-12 text-header">
            <div className="centered-content">
              <img
                src="https://easylearning.blob.core.windows.net/images-videos/icon-notification.png"
                style={{ width: "380px" }}
                alt="No data"
              />
              <h2 className="warning-message">
                Bạn chưa đăng ký bất kì khóa học nào!
              </h2>
              <h1>Vui lòng chọn khóa học bạn muốn để đăng ký.</h1>
              <p>
                Theo dõi website thường xuyên để cập nhật thông tin khóa học mới
                nhất, giúp bạn không bỏ lỡ bất kỳ cơ hội học nào!
              </p>
              <button
                className="explore-course-btn"
                onClick={() => navigate("/courses")}
              >
                Khám phá các khóa học
              </button>
            </div>
          </div>
        ) : (
          <div
            className="col-lg-12 text-header"
            style={{ marginBottom: "80px" }}
          >
            <div className="section-title text-center">
              <div className="title-text">
                <h2
                  className="h2-text-center"
                  style={{ marginTop: "28px", color: "#06BBCC" }}
                >
                  ------------------------------- Các khóa học của bạn
                  -------------------------------
                </h2>
              </div>
            </div>
          </div>
        )}
        <div className="container">
          <div className="row accordion" id="accordion">
            <div className="col-xl-12">
              <div className="row">
                <div className="col-lg-12">
                  {userCourse.map((itemCourse) => (
                    <div
                      key={itemCourse.courseId}
                      className="card product_list accordion-item"
                    >
                      <div className="card-header accordion-header">
                        <div
                          className="btn btn-link accordion-button collapsed"
                          data-bs-toggle="collapse"
                          data-bs-target={`#item_${itemCourse.courseId}`}
                          aria-expanded="false"
                        >
                          <div className="list_block">
                            <div className="list_image">
                              <a className="nav-item nav-link active">
                                <img
                                  style={{ marginBottom: "8px" }}
                                  src={itemCourse.courseImage}
                                  className="image-fit-contain"
                                  alt="img"
                                />
                                <span>
                                  {itemCourse.courseType === "ONLINE" ? (
                                    <>
                                      <i
                                        className="fas fa-globe"
                                        style={{ marginRight: "5px" }}
                                      ></i>
                                      Trực tuyến
                                    </>
                                  ) : (
                                    <>
                                      <i
                                        className="fas fa-chalkboard-teacher"
                                        style={{ marginRight: "5px" }}
                                      ></i>
                                      Trực tiếp
                                    </>
                                  )}
                                </span>
                              </a>
                            </div>
                            <div className="list_text">
                              <a className="nav-item nav-link active">
                                <p
                                  className="subtitle"
                                  style={{ fontSize: "20px" }}
                                >
                                  {itemCourse.courseName}
                                </p>

                                <div className="container-circular-progress">
                                  <div style={{ display: "flex" }}>
                                    <div
                                      className="circular-progress"
                                      style={{
                                        backgroundImage: `conic-gradient(#38c9d6 ${
                                          courseProgress[itemCourse.courseId] ||
                                          0
                                        }%, #808080 ${
                                          courseProgress[itemCourse.courseId] ||
                                          0
                                        }%)`,
                                      }}
                                    >
                                      <span className="progress-value">
                                        {courseProgress[itemCourse.courseId] ||
                                          0}
                                        %
                                      </span>
                                    </div>
                                    <div
                                      className="complete-course"
                                      style={{ marginTop: "20px" }}
                                    >
                                      <h6
                                        className="title"
                                        style={{ marginLeft: "20px" }}
                                      >
                                        <strong>
                                          {10} / {10}
                                        </strong>{" "}
                                        bài học
                                      </h6>
                                    </div>
                                  </div>
                                </div>
                              </a>
                            </div>
                          </div>
                        </div>
                      </div>

                      <div
                        id={`item_${itemCourse.courseId}`}
                        className="accordion-collapse collapse"
                      >
                        <div className="card-body accordion-body">
                          <div className="row">
                            {itemCourse.courseEventResponses.map(
                              (itemCourseEvent) => (
                                <div
                                  key={itemCourseEvent.id}
                                  className="col-sm-4 col-6"
                                >
                                  <a className="nav-item nav-link active">
                                    <div
                                      className="list_block_item"
                                      style={{ cursor: "pointer" }}
                                    >
                                      <p
                                        className="category"
                                        style={{ fontSize: "16px" }}
                                      >
                                        {itemCourseEvent.courseEventName}
                                      </p>
                                      <h6 className="title">
                                        <strong>
                                          {2} / {2}
                                        </strong>{" "}
                                        bài học
                                      </h6>
                                    </div>
                                  </a>
                                </div>
                              )
                            )}
                          </div>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </ClientShared>
  );
};

export default UserCourse;
