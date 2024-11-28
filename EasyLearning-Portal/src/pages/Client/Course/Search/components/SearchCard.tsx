import { useNavigate } from "react-router-dom";
import { Course, CourseSlim } from "../../../../../model/Course";
import { useEffect, useState } from "react";
import {
  ADD_TO_CART,
  GET_COURSE_DETAIL,
  GET_COURSE_STATUS_BY_USER,
} from "../../../../../constants/API";
import {
  DoCallAPIWithOutToken,
  DoCallAPIWithToken,
} from "../../../../../services/HttpService";
import { HTTP_OK } from "../../../../../constants/HTTPCode";
import { formatCurrency } from "../../../../../hooks/useCurrency";

interface CardProps {
  course: Course;
}

const SearchCard: React.FC<CardProps> = ({ course }) => {
  const navigate = useNavigate();
  const starRatings = [1, 2, 3, 4, 5];
  const [isPurchased, setIsPurchased] = useState(false);
  const [isInCart, setIsInCart] = useState(false);
  const [averageRating, setAverageRating] = useState<number | null>(null);
  const [totalLearningTime, setTotalLearningTime] = useState<string | null>(
    null
  );
  const [priceDiscount, setPriceDiscount] = useState<number | null>(null);

  const fetchCourseDetail = async (courseId: string) => {
    try {
      const URL = GET_COURSE_DETAIL + "/" + courseId;
      const response = await DoCallAPIWithOutToken(URL, "GET");
      if (response.status === HTTP_OK) {
        const data = await response.data.result;
        console.log(data.averageRating);
        setAverageRating(data.averageRating);
        setTotalLearningTime(data.totalLearningTime);
        setPriceDiscount(data.coursePriceDiscount);
      }
    } catch (error) {
      console.error("Error fetching course detail:", error);
    }
  };

  const doCallGetCourseStatus = async (courseId: string) => {
    try {
      const URL = GET_COURSE_STATUS_BY_USER + "/" + courseId;
      const response = await DoCallAPIWithToken(URL, "GET");
      if (response.status === HTTP_OK) {
        const data = await response.data.result;
        setIsInCart(data.isInCart);
        setIsPurchased(data.isPurchased);
      }
    } catch (error) {}
  };

  const addToCart = (courseId: string) => {
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

  const handleNavigateToCourseDetail = (courseId: string) => {
    navigate("/course/" + courseId);
  };

  useEffect(() => {
    if (course?.id) {
      fetchCourseDetail(course.id);
      doCallGetCourseStatus(course.id);
    }
  }, [course.id]);

  return (
    <div
      className="course-card"
      onClick={() => handleNavigateToCourseDetail(course.id)}
    >
      <div
        key={course.id}
        className="mb-3 p-3 border rounded d-flex align-items-center justify-content-between"
      >
        <div
          className="product-image me-3 d-flex justify-content-center align-items-center"
          style={{ width: "180px" }}
        >
          <img
            src={course.imageUrl}
            alt="Product"
            style={{
              width: "100%",
              height: "auto",
              objectFit: "contain",
            }}
          />
        </div>

        {/* Right side: Product Details */}
        <div className="flex-grow-1">
          <h5>{course.courseName}</h5>

          <p className="text-muted mb-1">{course.instructor}</p>
          <p className="mb-1" style={{ fontSize: "15px" }}>
            {starRatings.map((i) =>
              i <= (averageRating ?? 0) ? (
                <small className="fa fa-star text-warning "></small>
              ) : (
                <small className="fa fa-star text-mute"> </small>
              )
            )}{" "}
            ({course.feedbacks.length})
          </p>
          <p className="mb-1" style={{ fontSize: "14px" }}>
            {totalLearningTime} tổng số giờ - {course.trainingParts.length} bài
            giảng - cấp độ TẤT CẢ
          </p>
        </div>

        <div className="text-end" style={{ minWidth: "150px" }}>
          {priceDiscount ? (
            <>
              <p className="text-danger fs-5 mb-0">
                {" "}
                {formatCurrency(priceDiscount)}₫
              </p>
              <p className="text-muted text-decoration-line-through mb-0">
                {formatCurrency(course.coursePrice)}₫
              </p>
            </>
          ) : (
            <p className="text-danger fs-5 mb-0">
              {" "}
              {formatCurrency(course.coursePrice)}₫
            </p>
          )}
        </div>
      </div>

      <div className="hover-card" onClick={(e) => e.stopPropagation()}>
        <h4>Tổng quan</h4>
        <ul>
          <li>{course.courseDescription}</li>
        </ul>
        <div className="d-flex justify-content-center">
          {" "}
          <button onClick={() => addToCart(course.id)} className="add-to-cart">
            {isPurchased
              ? "Bắt Đầu Học Ngay"
              : isInCart
              ? "Đi Tới Giỏ Hàng  "
              : "Tham Gia Ngay"}
          </button>
        </div>
        <div className="hover-card-arrow"></div>
      </div>
    </div>
  );
};

export default SearchCard;
