import React, { useEffect, useState } from "react";
import { GET_COURSE_BY_USER } from "../../../../constants/API";
import { ApplicationResponse } from "../../../../model/BaseResponse";
import { CourseSlim } from "../../../../model/Course";
import { DoCallAPIWithToken } from "../../../../services/HttpService";
import ClientShared from "../../Shared/ClientShared";
import "./UserCourse.css";
import { useNavigate } from "react-router-dom";
const UserCourse: React.FC = () => {
  const [userCourse, getUserCourse] = useState<CourseSlim[]>([]);
  const navigator = useNavigate();
  const doCallGetCourseByUser = () => {
    DoCallAPIWithToken(GET_COURSE_BY_USER, "get").then((res) => {
      const response: ApplicationResponse<CourseSlim[]> = res.data;
      console.log(response.result);
      getUserCourse(response.result);
    });
  };
  useEffect(() => {
    doCallGetCourseByUser();
  }, []);
  return (
    <ClientShared>
      {userCourse.length === 0 ? (
        <div
          className="col-lg-12 text-header"
          style={{ marginTop: "120px", marginBottom: "120px" }}
        >
          <div className="section-title text-center">
            <div className="title-text">
              <h2 className="h2-text-center">
                Bạn chưa đăng kí bất kì một khóa học trực tiếp nào cả
              </h2>
            </div>
          </div>
        </div>
      ) : (
        <div className="col-lg-12 text-header" style={{ marginBottom: "80px" }}>
          <div className="section-title text-center">
            <div className="title-text">
              <h2 className="h2-text-center">Các khóa học của bạn</h2>
            </div>
          </div>
        </div>
      )}

      <div className="container">
        <div className="row accordion" id="accordion">
          <div className="col-xl-12">
            <div className="row">
              {userCourse.map((course) => (
                <>
                  <div className="col-lg-12">
                    <div
                      className="card product_list accordion-item"
                      onClick={() => navigator("/course/" + course.courseId)}
                    >
                      <div className="card-header accordion-header">
                        <div
                          className="btn btn-link collapsed"
                          data-bs-toggle="collapse"
                          data-bs-target="#item_@itemCourse.Id"
                          aria-expanded="false"
                        >
                          <div className="list_block">
                            <div className="list_image">
                              <a className="nav-item nav-link active">
                                <img
                                  src={course.courseImage}
                                  className="image-fit-contain"
                                  alt="img"
                                />
                              </a>
                            </div>
                            <div className="list_text">
                              <a className="nav-item nav-link active">
                                <p
                                  className="subtitle"
                                  style={{ fontSize: "20px" }}
                                >
                                  {course.courseName}
                                </p>
                                <div className="container-circular-progress">
                                  <div style={{ display: "flex" }}>
                                    <div
                                      className="complete-course"
                                      style={{ marginTop: "20px" }}
                                    >
                                      <h6
                                        className="title"
                                        style={{ marginLeft: "20px" }}
                                      >
                                        {" "}
                                        <strong>
                                          {course.totalTrainingPartByCourse}
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
                    </div>
                  </div>
                </>
              ))}
              {}
            </div>
          </div>
        </div>
      </div>
    </ClientShared>
  );
};

export default UserCourse;
