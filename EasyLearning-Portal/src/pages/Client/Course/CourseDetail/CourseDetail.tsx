import React, { useEffect, useState } from "react";
import ClientShared from "../../Shared/ClientShared";
import { useNavigate, useParams } from "react-router-dom";
import { CourseSlim } from "../../../../model/Course";
import {
  ADD_TO_CART,
  GET_COURSE_DETAIL,
  GET_COURSE_STATUS,
  GET_FEEDBACKS_FOR_COURSE,
} from "../../../../constants/API";
import {
  DoCallAPIWithOutToken,
  DoCallAPIWithToken,
} from "../../../../services/HttpService";

import "./CourseDetail.css";

import classNames from "classnames";
import { Feedback } from "../../../../model/FeedBack";
import { isUserLogin } from "../../../../hooks/useLogin";
import { HTTP_OK } from "../../../../constants/HTTPCode";

const CourseDetail: React.FC = () => {
  const navigate = useNavigate();
  const { courseId } = useParams<{ courseId: string }>();
  const [course, setCourse] = useState<CourseSlim>();
  const [feedBacks, setFeedBacks] = useState<Feedback[]>([]);
  const starRatings = [1, 2, 3, 4, 5];
  const [isPurchased, setIsPurchased] = useState(false);
  const [isInCart, setIsInCart] = useState(false);
  const isLogin = isUserLogin();
  const LESSON = "LESSON";

  const fetchCourseDetail = async (courseId: string) => {
    try {
      const URL = GET_COURSE_DETAIL + "/" + courseId;
      const courseRes = await DoCallAPIWithOutToken(URL, "GET");
      const courseDetail: CourseSlim = courseRes.data.result;
      setCourse(courseDetail);

      const feedbackURL = GET_FEEDBACKS_FOR_COURSE + "/" + courseId;
      const feedbackRes = await DoCallAPIWithOutToken(feedbackURL, "GET");
      setFeedBacks(feedbackRes.data.result.feedbacks);
    } catch (e) {
      console.log(e);
    }
  };

  const fetchCourseStatus = async (courseId: string) => {
    try {
      const URL = GET_COURSE_STATUS + "/" + courseId;
      const response = await DoCallAPIWithToken(URL, "GET");
      if (response.status === HTTP_OK) {
        const data = await response.data.result;
        setIsInCart(data.isInCart);
        setIsPurchased(data.isPurchased);
      }
    } catch (error) {}
  };

  const [openEvents, setOpenEvents] = useState<{ [key: string]: boolean }>({});
  const toggleEventDetails = (eventName: string) => {
    setOpenEvents((prev) => ({
      ...prev,
      [eventName]: !prev[eventName],
    }));
  };

  useEffect(() => {
    if (courseId) {
      fetchCourseDetail(courseId);
      fetchCourseStatus(courseId);

      setTimeout(() => {
        window.scrollTo(0, 0);
      }, 0);
    } else {
      console.error("Course ID is missing!");
    }
  }, [courseId]);

  const addToCart = () => {
    if (!isLogin) {
      localStorage.clear();
      navigate("/login");
    }
    if (isPurchased) {
      navigate("/userCourses");
    } else if (isInCart) {
      navigate("/shoppingCart");
    } else {
      const URL = ADD_TO_CART + "/" + courseId;

      DoCallAPIWithToken(URL, "post").then((res) => {
        if (res.status === HTTP_OK) {
          navigate("/shoppingCart");
        }
      });
    }
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
                  {/* <button
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
                  </button> */}
                </div>
              </div>
            </div>

            <div
              className="col-md-6 col-sm-12 col-12"
              style={{ marginLeft: "28px", flex: 1 }}
            >
              <h2 className="name">
                {course?.courseName}

                <small style={{ marginTop: "10px" }}>
                  {" "}
                  Khóa học bởi
                  <a style={{ color: "#06BBCC", marginLeft: "10px" }}>
                    {course?.instructor}
                  </a>
                </small>
                <span className="fa fa-2x">
                  {starRatings.map((x) =>
                    x <= (course?.averageRating ?? 0) ? (
                      <i className="fas fa-star fa-2x text-primary" />
                    ) : (
                      <i className="fas fa-star fa-2x text-muted" />
                    )
                  )}

                  <span className="fa fa-2x">
                    {" "}
                    <h5 style={{ marginLeft: "10px" }}>
                      {" "}
                      ({course?.totalFeedback}) Lượt đánh giá
                    </h5>
                  </span>
                </span>
              </h2>
              <hr />
              <h3 className="price-container">{course?.coursePrice}.000 VNĐ</h3>
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
                          <br />
                          <dt
                            className="toggle-details"
                            onClick={() =>
                              toggleEventDetails(classEvent.courseEventName)
                            }
                          >
                            {classEvent.courseEventName} &nbsp;
                            <span className="icon">
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
                                  <dd
                                    className={classNames("training-part", {
                                      free: part.free,
                                      "not-free": !part.free,
                                    })}
                                  >
                                    <span>
                                      {part.trainingPartType == LESSON ? (
                                        <i className="fas fa-tv"></i>
                                      ) : (
                                        <i className="far fa-file-alt"></i>
                                      )}
                                      <span
                                        className={classNames({
                                          underline: part.free,
                                        })}
                                      >
                                        {/* {" "} */}
                                        {part.trainingPartName}
                                      </span>
                                    </span>
                                    {part.free && (
                                      <span className="preview-text">
                                        Xem thử
                                      </span>
                                    )}
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

                    <div className="chat-body no-padding profile-message">
                      <ul>
                        {feedBacks.map((itemFeedback, index) => (
                          <li
                            className="message row"
                            key={index}
                            style={{
                              borderBottom: "1px solid #ddd",
                              marginBottom: "10px",
                              marginTop: "28px",
                            }}
                          >
                            <div className="col-md-1">
                              <img
                                src={itemFeedback.avatar}
                                className="online"
                              />
                            </div>
                            <div className="col-md-11">
                              <span className="message-text">
                                <div
                                  style={{
                                    display: "flex",
                                    alignItems: "center",
                                    marginBottom: "10px",
                                  }}
                                >
                                  <a className="username">
                                    <span
                                      style={{ justifyContent: "flex-start" }}
                                    >
                                      <span className="feedback-userName-background">
                                        {" "}
                                        {itemFeedback.fullNameUser}
                                      </span>

                                      <span style={{ fontSize: "14px" }}>
                                        {" "}
                                        - {itemFeedback.typeUser}{" "}
                                      </span>
                                    </span>
                                  </a>

                                  <span
                                    className="rating-and-report"
                                    style={{
                                      justifyContent: "flex-end",
                                      alignItems: "center",
                                      marginLeft: "auto",
                                    }}
                                  >
                                    <span className="star-rating">
                                      {starRatings.map((i) => (
                                        <i
                                          key={i}
                                          className={`fas fa-star fa-2x ${
                                            i <= itemFeedback.feedbackRating
                                              ? "text-primary"
                                              : "text-muted"
                                          }`}
                                        />
                                      ))}
                                    </span>
                                    <i
                                      className="fas fa-ellipsis-h report-icon"
                                      style={{
                                        float: "right",
                                        cursor: "pointer",
                                      }}
                                      onClick={() => alert("Report feedback")}
                                      title="Report this feedback"
                                    />
                                  </span>
                                </div>

                                <small
                                  className="text-muted pull-right ultra-light"
                                  style={{ fontSize: "16px" }}
                                >
                                  {" "}
                                  {itemFeedback.content}
                                </small>
                              </span>
                              <ul className="list-inline font-xs">
                                <li className="pull-right"></li>
                              </ul>
                            </div>
                          </li>
                        ))}
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
              <hr />
              <div className="row">
                <a
                  onClick={() => addToCart()}
                  className="flex-shrink-0 btn btn-sm btn-primary px-3"
                  style={{
                    borderRadius: "30px",
                    fontSize: "18px",
                  }}
                >
                  {isPurchased
                    ? "Bắt Đầu Học Ngay"
                    : isInCart
                    ? "Đi Tới Giỏ Hàng  "
                    : "Tham Gia Ngay"}
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </ClientShared>
  );
};

export default CourseDetail;
