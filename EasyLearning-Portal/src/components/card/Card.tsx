import React, { useEffect, useState } from "react";
import { Course } from "../../model/Course";
import {
  DoCallAPIWithOutToken,
  DoCallAPIWithToken,
} from "../../services/HttpService";
import {
  ADD_TO_CART,
  GET_COURSE_DETAIL,
  GET_COURSE_STATUS,
} from "../../constants/API";
import { HTTP_OK } from "../../constants/HTTPCode";
import { useNavigate } from "react-router-dom";
import { isUserLogin } from "../../hooks/useLogin";
interface CardProps {
  course: Course;
}

const Card: React.FC<CardProps> = ({ course }) => {
  const navigate = useNavigate();
  const starRatings = [1, 2, 3, 4, 5];
  const [averageRating, setAverageRating] = useState<number | null>(null);
  const [isPurchased, setIsPurchased] = useState(false);
  const [isInCart, setIsInCart] = useState(false);
  const isLogin = isUserLogin();

  const fetchCourseDetail = async (courseId: string) => {
    try {
      const URL = GET_COURSE_DETAIL + "/" + courseId;
      const response = await DoCallAPIWithOutToken(URL, "GET");
      if (response.status === HTTP_OK) {
        const data = await response.data.result;
        setAverageRating(data.averageRating);
      }
    } catch (error) {
      console.error("Error fetching course detail:", error);
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

  useEffect(() => {
    if (course?.id) {
      fetchCourseDetail(course.id);
      fetchCourseStatus(course.id);
    }
  }, [course.id]);

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
      const URL = ADD_TO_CART + "/" + course.id;

      DoCallAPIWithToken(URL, "post").then((res) => {
        if (res.status === HTTP_OK) {
          navigate("/shoppingCart");
        }
      });
    }
  };

  return (
    <div className="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.3s">
      <div className="course-item bg-light">
        <div className="position-relative overflow-hidden">
          <img className="img-fluid" src={course.imageUrl} alt="" />
          <div className="w-100 d-flex justify-content-center position-absolute bottom-0 start-0 mb-4">
            <a
              href={"/course/" + course.id}
              className="flex-shrink-0 btn btn-sm btn-primary px-3 border-end"
              style={{ borderRadius: "30px 0 0 30px" }}
            >
              Xem Thêm
            </a>
            <a
              onClick={() => addToCart()}
              className="flex-shrink-0 btn btn-sm btn-primary px-3"
              style={{ borderRadius: "0 30px 30px 0" }}
            >
              {isPurchased
                ? "Bắt Đầu Học Ngay"
                : isInCart
                ? "Đi Tới Giỏ Hàng  "
                : "Tham Gia Ngay"}
            </a>
          </div>
        </div>
        <div className="text-center p-3 pb-0">
          <h3 className="mb-0">{course.coursePrice}.000 VNĐ</h3>
          <div className="mb-2">
            {starRatings.map((i) =>
              i <= (averageRating ?? 0) ? (
                <small className="fa fa-star text-warning"></small>
              ) : (
                <small className="fa fa-star text-mute"></small>
              )
            )}
            <small style={{ marginLeft: "10px" }}>
              ({course.feedbacks.length})
            </small>
          </div>
          <h5 className="mb-2">{course.courseName}</h5>
        </div>
        <div className="d-flex border-top">
          <small className="flex-fill text-center border-end py-2">
            <i className="fa fa-user-tie text-primary me-2"></i>
            &nbsp;{course.instructor}
          </small>
          <small className="flex-fill text-center border-end py-2">
            <i className="fa fa-clock text-primary me-2"></i>
            {course.trainingParts.length}
          </small>
          <small className="flex-fill text-center py-2">
            <i className="fa fa-user text-primary me-2"></i>
            {course.registeredUsers}
          </small>
        </div>
      </div>
    </div>
  );
};

export default Card;
