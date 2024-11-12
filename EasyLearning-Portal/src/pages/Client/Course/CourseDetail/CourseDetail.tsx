import React, { useEffect, useState } from "react";
import ClientShared from "../../Shared/ClientShared";
import { useParams } from "react-router-dom";
import { CourseSlim } from "../../../../model/Course";
import { GET_COURSE_DETAIL } from "../../../../constants/API";
import { DoCallAPIWithOutToken } from "../../../../services/HttpService";


import "./CourseDetail.css";


const CourseDetail: React.FC = () => {

  
  const { courseId } = useParams();
  const [course, setCourse] = useState<CourseSlim>();
  const numbers = [1, 2, 3, 4, 5];
  
  useEffect(() => {
    const URL = GET_COURSE_DETAIL + "/" + courseId;
    DoCallAPIWithOutToken(URL, "get")
      .then((res) => {
        const courseDetail: CourseSlim = res.data.result;
        setCourse(courseDetail);
      })
      .catch((e) => {
        console.log(e);
      });
  }, []);

  const LESSON = "LESSON";

  const [openEvents, setOpenEvents] = useState<{ [key: string]: boolean }>({});
  const toggleEventDetails = (eventName: string) => {
    setOpenEvents((prev) => ({
      ...prev,
      [eventName]: !prev[eventName], // Đảo ngược trạng thái hiển thị chi tiết
    }));
  };



  return (
    <ClientShared>
      <div className="container">
        <div className="product-content product-wrap clearfix product-deatil">
          <div className="row">
            <div className="col-md-5 col-sm-12 col-12">
              <div className="product-image">
                <div id="myCarousel-2" className="carousel slide">
                  <div className="carousel-inner">
                    <div className="carousel-item active">
                      <img
                        className="img-fluid"
                        alt=""
                        src={course?.courseImage}
                      />
                    </div>
                  </div>
                  <button
                    className="carousel-control-prev"
                    type="button"
                    data-bs-target="#myCarousel-2"
                    data-bs-slide="prev"
                  >
                    <span className="visually-hidden">Trước</span>
                  </button>
                  <button
                    className="carousel-control-next"
                    type="button"
                    data-bs-target="#myCarousel-2"
                    data-bs-slide="next"
                  >
                    <span className="visually-hidden">Sau</span>
                  </button>
                </div>
              </div>
            </div>

            <div className="col-md-6 col-sm-12 col-12">
              <h2 className="name">
                <small>Khóa học bởi {course?.nameInstructor}</small>
                <span className="fa fa-2x">
                  {course?.averageRating &&
                    numbers.map((x) =>
                      x < course?.averageRating ? (
                        <i className="fas fa-star fa-2x text-primary" />
                      ) : (
                        <i className="fas fa-star fa-2x text-muted" />
                      )
                    )}
                  <h5>{course?.totalFeedback} Lượt đánh giá</h5>
                </span>
              </h2>
              <hr />
              <h3 className="price-container">{course?.coursePrice}.000 VND</h3>
              <hr />
              <div className="description description-tabs">
                <ul className="nav nav-pills">
                  <li className="nav-item">
                    <a
                      href="#specifications"
                      data-bs-toggle="tab"
                      className="nav-link active no-margin"
                    >
                      Nội dung khóa học
                    </a>
                  </li>
                  <li className="nav-item">
                    <a
                      href="#more-information"
                      data-bs-toggle="tab"
                      className="nav-link "
                    >
                      Chi tiết khóa học
                    </a>
                  </li>
                  <li className="nav-item">
                    <a
                      href="#reviews"
                      data-bs-toggle="tab"
                      className="nav-link"
                    >
                      Đánh giá
                    </a>
                  </li>
                </ul>
                <div className="tab-content">
                  <div
                    className="tab-pane fade show active"
                    id="specifications"
                  >
                    <br />
                    <dl>
                      {course?.courseEventResponses.map((classEvent) => (
                        <>
                          <br/>
                          <dt className="toggle-details"  onClick={() => toggleEventDetails(classEvent.courseEventName)}>
                          {classEvent.courseEventName} &nbsp;
                            <span className="icon" >
                            <i
                                className={`fas ${
                                  openEvents[classEvent.courseEventName]
                                    ? "fa-chevron-up"
                                    : "fa-chevron-down"
                                }`}
                              ></i>
                            </span>
                          </dt>
                          {openEvents[classEvent.courseEventName] && (
                          <div className="details">
                            {classEvent.trainingParts.map((part) => (
                              <>
                                <dd>
                                  
                                  <span>
                                    {part.trainingPartType == LESSON ? (
                                      <i className="fas fa-tv"></i>
                                    ) : (
                                      <i className="far fa-file-alt"></i>
                                    )}
                                  </span>{" "}
                                  {part.trainingPartName}
                                </dd>
                              </>
                            ))}
                          </div>
                          )}
                        </>
                      ))}
                      <br />
                    </dl>
                  </div>

                  <div className="tab-pane fade " id="more-information">
                    <br />
                    <p>{course?.courseName}</p>
                  </div>

                  <div className="tab-pane fade" id="reviews">
                    <br />

                    <form method="post" className="well padding-bottom-10">
                      <div className="form-group">
                        <div className="form-group">
                          <textarea
                            id="textContent"
                            name="content"
                            rows={2}
                            className="form-control"
                            placeholder="Write a review"
                          ></textarea>
                        </div>
                        <div className="d-flex justify-content-end ml-auto">
                          <input
                            type="hidden"
                            id="rating"
                            name="rating"
                            value="0"
                          />
                          <span
                            className="star"
                            style={{ fontSize: "25px", cursor: "pointer" }}
                            data-value="1"
                          >
                            &#9733;
                          </span>
                          <span
                            className="star"
                            style={{ fontSize: "25px", cursor: "pointer" }}
                            data-value="2"
                          >
                            &#9733;
                          </span>
                          <span
                            className="star"
                            style={{ fontSize: "25px", cursor: "pointer" }}
                            data-value="3"
                          >
                            &#9733;
                          </span>
                          <span
                            className="star"
                            style={{ fontSize: "25px", cursor: "pointer" }}
                            data-value="4"
                          >
                            &#9733;
                          </span>
                          <span
                            className="star"
                            style={{ fontSize: "25px", cursor: "pointer" }}
                            data-value="5"
                          >
                            &#9733;
                          </span>
                        </div>
                        <button
                          id="submitReviewBtn"
                          type="submit"
                          className="btn btn-sm btn-primary pull-right"
                        >
                          Submit Review
                        </button>
                        <a
                          className="btn btn-link profile-link-btn"
                          rel="tooltip"
                          data-bs-placement="bottom"
                          title="Add Location"
                        >
                          <i className="fa fa-location-arrow"></i>
                        </a>
                        <a
                          className="btn btn-link profile-link-btn"
                          rel="tooltip"
                          data-bs-placement="bottom"
                          title="Add Voice"
                        >
                          <i className="fa fa-microphone"></i>
                        </a>
                        <a
                          className="btn btn-link profile-link-btn"
                          rel="tooltip"
                          data-bs-placement="bottom"
                          title="Add Photo"
                        >
                          <i className="fa fa-camera"></i>
                        </a>
                        <a
                          className="btn btn-link profile-link-btn"
                          rel="tooltip"
                          data-bs-placement="bottom"
                          title="Add File"
                        >
                          <i className="fa fa-file"></i>
                        </a>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
              <hr />
            </div>
          </div>
        </div>
      </div>
    </ClientShared>
  );
};

export default CourseDetail;
