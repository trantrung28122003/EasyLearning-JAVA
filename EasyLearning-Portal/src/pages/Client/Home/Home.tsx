import React, { useEffect, useState } from "react";
import styled from "./Home.module.css";
import ClientShared from "../Shared/ClientShared";
import AboutImg from "../../../assets/img/about.jpg";
import Cat1Img from "../../../assets/img/cat-1.jpg";
import Cat2Img from "../../../assets/img/cat-2.jpg";
import Cat3Img from "../../../assets/img/cat-3.jpg";
import Cat4Img from "../../../assets/img/cat-4.jpg";
import { DoCallAPIWithOutToken } from "../../../services/HttpService";
import { HTTP_OK } from "../../../constants/HTTPCode";
import { GET_COURSES_MOST_REGISTERED } from "../../../constants/API";
import { ApplicationResponse } from "../../../model/BaseResponse";
import Card from "../../../components/card/Card";
import { Course } from "../../../model/Course";
import { Feedback } from "../../../model/FeedBack";
const Home: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const doGetCourses = () => {
    DoCallAPIWithOutToken(GET_COURSES_MOST_REGISTERED, "get").then((res) => {
      if (res.status === HTTP_OK) {
        const response: ApplicationResponse<Course[]> = res.data;
        setCourses(response.result);
      }
    });
  };

  const feedbacks: Feedback[] = [];
  courses.forEach((course) => {
    feedbacks.concat(course.feedbacks);
  });

  useEffect(() => {
    doGetCourses();
  }, []);

  return (
    <ClientShared>
      <div className={styled.home}>
        <div className="container-xxl py-5">
          <div className="container">
            <div className="row g-4">
              <div
                className="col-lg-3 col-sm-6 wow fadeInUp"
                data-wow-delay="0.1s"
              >
                <div className="service-item text-center pt-3">
                  <div className="p-4">
                    <i className="fa fa-3x fa-graduation-cap text-primary mb-4"></i>
                    <h5 className="mb-3">Giảng Viên Chuyên Nghiệp</h5>
                    <p>
                      Phơng pháp giảng dạy linh hoạt và có kinh nghiệm trong
                      lĩnh vực chuyên môn
                    </p>
                  </div>
                </div>
              </div>
              <div
                className="col-lg-3 col-sm-6 wow fadeInUp"
                data-wow-delay="0.3s"
              >
                <div className="service-item text-center pt-3">
                  <div className="p-4">
                    <i className="fa fa-3x fa-globe text-primary mb-4"></i>
                    <h5 className="mb-3">Đăng Ký Trực Tuyến</h5>
                    <p>
                      Thuận tiện cho việc đăng ký, lựa chọn thời gian và lịch
                      học phù hợp với lịch trình cá nhân
                    </p>
                  </div>
                </div>
              </div>
              <div
                className="col-lg-3 col-sm-6 wow fadeInUp"
                data-wow-delay="0.5s"
              >
                <div className="service-item text-center pt-3">
                  <div className="p-4">
                    <i className="fa fa-3x fa-home text-primary mb-4"></i>
                    <h5 className="mb-3">Các Dự Án Tại Nhà</h5>
                    <p>
                      Không bị giới hạn bởi môi trường làm việc truyền thống, có
                      thể chọn thời gian làm việc phù hợp với lịch trình cá nhân
                      và tạo điều kiện tốt nhất để sáng tạo
                    </p>
                  </div>
                </div>
              </div>
              <div
                className="col-lg-3 col-sm-6 wow fadeInUp"
                data-wow-delay="0.7s"
              >
                <div className="service-item text-center pt-3">
                  <div className="p-4">
                    <i className="fa fa-3x fa-book-open text-primary mb-4"></i>
                    <h5 className="mb-3">Thư Viện Tài Liệu</h5>
                    <p>
                      Cung cấp một loạt các tài liệu và tài nguyên khác nhau, từ
                      sách, bài báo, đến bản ghi và hướng dẫn, giúp bạn nắm vững
                      thông tin cần thiết cho dự án của mình
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="container-xxl py-5">
          <div className="container">
            <div className="row g-5">
              <div
                className="col-lg-6 wow fadeInUp"
                data-wow-delay="0.1s"
                style={{ minHeight: "400px" }}
              >
                <div className="position-relative h-100">
                  <img
                    className="img-fluid position-absolute w-100 h-100"
                    src={AboutImg}
                    alt=""
                    style={{ objectFit: "cover" }}
                  />
                </div>
              </div>
              <div className="col-lg-6 wow fadeInUp" data-wow-delay="0.3s">
                <h6 className="section-title bg-white text-start text-primary pe-3">
                  Về Chúng Tôi
                </h6>
                <h1 className="mb-4">Chào mừng đến với eLEARNING</h1>
                <p className="mb-4">
                  Tại eLEARNING , chúng tôi tin rằng học tập không bao giờ ngừng
                  lại và mỗi người đều có khả năng phát triển bản thân. Chính vì
                  vậy, chúng tôi đã thành lập eLEARNING với mục tiêu giúp mọi
                  người tiếp cận kiến thức và kỹ năng mới một cách dễ dàng và
                  linh hoạt.
                </p>
                <p className="mb-4">
                  Hãy đồng hành cùng chúng tôi trên con đường phát triển cá nhân
                  và chuyên môn của bạn. Đăng ký ngay hôm nay để bắt đầu hành
                  trình mới của bạn!
                </p>
                <div className="row gy-2 gx-4 mb-4">
                  <div className="col-sm-6">
                    <p className="mb-0">
                      <i className="fa fa-arrow-right text-primary me-2"></i>
                      Giảng Viên Chuyên Nghiệp
                    </p>
                  </div>
                  <div className="col-sm-6">
                    <p className="mb-0">
                      <i className="fa fa-arrow-right text-primary me-2"></i>
                      Đăng Ký Học Trực Tuyến
                    </p>
                  </div>
                  <div className="col-sm-6">
                    <p className="mb-0">
                      <i className="fa fa-arrow-right text-primary me-2"></i>Cấp
                      Giấy Chứng Nhận Quốc Tế
                    </p>
                  </div>
                  <div className="col-sm-6">
                    <p className="mb-0">
                      <i className="fa fa-arrow-right text-primary me-2"></i>
                      Thực Hiện Dự Án Tại Nhà
                    </p>
                  </div>
                  <div className="col-sm-6">
                    <p className="mb-0">
                      <i className="fa fa-arrow-right text-primary me-2"></i>
                      Tiết Kiệm Chi
                    </p>
                  </div>
                  <div className="col-sm-6">
                    <p className="mb-0">
                      <i className="fa fa-arrow-right text-primary me-2"></i>
                      Thời Gian Linh Hoạt
                    </p>
                  </div>
                </div>
                <a className="btn btn-primary py-3 px-5 mt-2" href="">
                  Xem Thêm
                </a>
              </div>
            </div>
          </div>
        </div>

        <div className="container-xxl py-5 category">
          <div className="container">
            <div className="text-center wow fadeInUp" data-wow-delay="0.1s">
              <h6 className="section-title bg-white text-center text-primary px-3">
                Danh Mục
              </h6>
              <h1 className="mb-5">Danh Mục Các Khóa Học</h1>
            </div>
            <div className="row g-3">
              <div className="col-lg-7 col-md-6">
                <div className="row g-3">
                  <div
                    className="col-lg-12 col-md-12 wow zoomIn"
                    data-wow-delay="0.1s"
                  >
                    <a
                      className="position-relative d-block overflow-hidden"
                      href=""
                    >
                      <img className="img-fluid" src={Cat1Img} alt="" />
                      <div
                        className="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3"
                        style={{ margin: "1px" }}
                      >
                        <h5 className="m-0">Thiết Kế Web</h5>
                        <small className="text-primary">49 Courses</small>
                      </div>
                    </a>
                  </div>
                  <div
                    className="col-lg-6 col-md-12 wow zoomIn"
                    data-wow-delay="0.3s"
                  >
                    <a
                      className="position-relative d-block overflow-hidden"
                      href=""
                    >
                      <img className="img-fluid" src={Cat2Img} alt="" />
                      <div
                        className="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3"
                        style={{ margin: "1px" }}
                      >
                        <h5 className="m-0">Thiết Kế Đồ Họa</h5>
                        <small className="text-primary">49 Courses</small>
                      </div>
                    </a>
                  </div>
                  <div
                    className="col-lg-6 col-md-12 wow zoomIn"
                    data-wow-delay="0.5s"
                  >
                    <a
                      className="position-relative d-block overflow-hidden"
                      href=""
                    >
                      <img className="img-fluid" src={Cat3Img} alt="" />
                      <div
                        className="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3"
                        style={{ margin: "1px" }}
                      >
                        <h5 className="m-0">Chỉnh Sửa Video</h5>
                        <small className="text-primary">49 Courses</small>
                      </div>
                    </a>
                  </div>
                </div>
              </div>
              <div
                className="col-lg-5 col-md-6 wow zoomIn"
                data-wow-delay="0.7s"
                style={{ minHeight: "350px" }}
              >
                <a
                  className="position-relative d-block h-100 overflow-hidden"
                  href=""
                >
                  <img
                    className="img-fluid position-absolute w-100 h-100"
                    src={Cat4Img}
                    alt=""
                    style={{ objectFit: "cover" }}
                  />
                  <div
                    className="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3"
                    style={{ margin: "1px" }}
                  >
                    <h5 className="m-0">Tiếp Thị Trực Tuyến</h5>
                    <small className="text-primary">49 Courses</small>
                  </div>
                </a>
              </div>
            </div>
          </div>
        </div>

        <div className="container-xxl py-5">
          <div className="container">
            <div className="text-center wow fadeInUp" data-wow-delay="0.1s">
              <h6 className="section-title bg-white text-center text-primary px-3">
                Các Khóa Học
              </h6>
              <h1 className="mb-5">Các Khóa Học Nổi Tiếng</h1>
            </div>
            <div className="row g-4 justify-content-center">
              {courses.map((course, index: number) => {
                return <Card course={course} key={index} />;
              })}
            </div>
          </div>
        </div>

        <div className="container-xxl py-5">
          <div className="container">
            <div className="text-center wow fadeInUp" data-wow-delay="0.1s">
              <h6 className="section-title bg-white text-center text-primary px-3">
                Giảng Viên
              </h6>
              <h1 className="mb-5">Các Giảng Viên</h1>
            </div>
            <div className="row g-4">
              <div
                className="col-lg-3 col-md-6 wow fadeInUp"
                data-wow-delay="0.1s"
              >
                <div className="team-item bg-light">
                  <div className="overflow-hidden">
                    <img className="img-fluid" src="img/team-1.jpg" alt="" />
                  </div>
                  <div
                    className="position-relative d-flex justify-content-center"
                    style={{ marginTop: "-23px" }}
                  >
                    <div className="bg-light d-flex justify-content-center pt-2 px-1">
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-facebook-f"></i>
                      </a>
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-twitter"></i>
                      </a>
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-instagram"></i>
                      </a>
                    </div>
                  </div>
                  <div className="text-center p-4">
                    <h5 className="mb-0">Trần Anh Tuấn</h5>
                    <small>Vị Trí/ Chức Vụ</small>
                  </div>
                </div>
              </div>
              <div
                className="col-lg-3 col-md-6 wow fadeInUp"
                data-wow-delay="0.3s"
              >
                <div className="team-item bg-light">
                  <div className="overflow-hidden">
                    <img
                      className="img-fluid"
                      src={
                        "https://www.facebook.com/23cfdcc4-9f2c-4db9-978a-91e7df1f65fe"
                      }
                      alt=""
                    />
                  </div>
                  <div
                    className="position-relative d-flex justify-content-center"
                    style={{ marginTop: "-23px" }}
                  >
                    <div className="bg-light d-flex justify-content-center pt-2 px-1">
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-facebook-f"></i>
                      </a>
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-twitter"></i>
                      </a>
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-instagram"></i>
                      </a>
                    </div>
                  </div>
                  <div className="text-center p-4">
                    <h5 className="mb-0">Trần Anh Tuấn</h5>
                    <small>Vị Trí/ Chức Vụ</small>
                  </div>
                </div>
              </div>
              <div
                className="col-lg-3 col-md-6 wow fadeInUp"
                data-wow-delay="0.5s"
              >
                <div className="team-item bg-light">
                  <div className="overflow-hidden">
                    <img className="img-fluid" src="img/team-3.jpg" alt="" />
                  </div>
                  <div
                    className="position-relative d-flex justify-content-center"
                    style={{ marginTop: "-23px" }}
                  >
                    <div className="bg-light d-flex justify-content-center pt-2 px-1">
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-facebook-f"></i>
                      </a>
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-twitter"></i>
                      </a>
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-instagram"></i>
                      </a>
                    </div>
                  </div>
                  <div className="text-center p-4">
                    <h5 className="mb-0">Trần Anh Tuấn</h5>
                    <small>Vị Trí/ Chức Vụ</small>
                  </div>
                </div>
              </div>
              <div
                className="col-lg-3 col-md-6 wow fadeInUp"
                data-wow-delay="0.7s"
              >
                <div className="team-item bg-light">
                  <div className="overflow-hidden">
                    <img className="img-fluid" src="img/team-4.jpg" alt="" />
                  </div>
                  <div
                    className="position-relative d-flex justify-content-center"
                    style={{ marginTop: "-23px" }}
                  >
                    <div className="bg-light d-flex justify-content-center pt-2 px-1">
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-facebook-f"></i>
                      </a>
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-twitter"></i>
                      </a>
                      <a className="btn btn-sm-square btn-primary mx-1" href="">
                        <i className="fab fa-instagram"></i>
                      </a>
                    </div>
                  </div>
                  <div className="text-center p-4">
                    <h5 className="mb-0">Trần Anh Tuấn</h5>
                    <small>Vị Trí/ Chức Vụ</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* <div className="container-xxl py-5 wow fadeInUp" data-wow-delay="0.1s">
          <div className="container">
            <div className="text-center">
              <h6 className="section-title bg-white text-center text-primary px-3">
                Phản Hồi
              </h6>
              <h1 className="mb-5">Đánh Giá Từ Các Học Viên</h1>
            </div>
            <div className="owl-carousel testimonial-carousel position-relative">
              <div className="testimonial-item text-center">
                <img
                  className="border rounded-circle p-2 mx-auto mb-3"
                  src="../../../assets/img/testimonial-1.jpg"
                  style={{ width: "80px", height: "80px" }}
                />
                <h5 className="mb-0">Trần Anh Tuấn</h5>
                <p>Profession</p>
                <div className="testimonial-text bg-light text-center p-4">
                  <p className="mb-0">
                    Tôi rất ấn tượng với chất lượng của khóa học. Giảng viên rất
                    am hiểu và dễ thương, luôn sẵn lòng giải đáp mọi thắc mắc
                    của tôi. Các tài liệu và tài nguyên hỗ trợ rất phong phú và
                    hữu ích. Tôi đã cảm thấy tự tin hơn trong lĩnh vực này sau
                    khi hoàn thành khóa học.
                  </p>
                </div>
              </div>
              <div className="testimonial-item text-center">
                <img
                  className="border rounded-circle p-2 mx-auto mb-3"
                  src="img/testimonial-2.jpg"
                  style={{ width: "80px", height: "80px" }}
                />
                <h5 className="mb-0">Trần Anh Tuấn</h5>
                <p>Profession</p>
                <div className="testimonial-text bg-light text-center p-4">
                  <p className="mb-0">
                    Tôi đã tìm kiếm một khóa học về Digital Marketing lâu nay và
                    cuối cùng tôi đã tìm thấy nó ở đây! Khóa học cung cấp một
                    cái nhìn toàn diện về Digital Marketing và hướng dẫn từ cơ
                    bản đến nâng cao. Các bài giảng được trình bày một cách sinh
                    động và dễ hiểu, giúp tôi tiếp cận với những khái niệm khó
                    hiểu một cách dễ dàng. Tôi rất hài lòng với trải nghiệm học
                    tập của mình ở đây
                  </p>
                </div>
              </div>
              <div className="testimonial-item text-center">
                <img
                  className="border rounded-circle p-2 mx-auto mb-3"
                  src="img/testimonial-3.jpg"
                  style={{ width: "80px", height: "80px" }}
                />
                <h5 className="mb-0">Trần Anh Tuấn</h5>
                <p>Profession</p>
                <div className="testimonial-text bg-light text-center p-4">
                  <p className="mb-0">
                    Đây là một trang web tuyệt vời cho những ai muốn phát triển
                    kỹ năng cá nhân và chuyên môn. Khóa học rất thú vị và thách
                    thức, giúp tôi mở rộng kiến thức và nâng cao khả năng của
                    mình. Tôi đã học được rất nhiều điều mới và không thể chờ
                    đợi để áp dụng chúng vào thực tế.
                  </p>
                </div>
              </div>
              <div className="testimonial-item text-center">
                <img
                  className="border rounded-circle p-2 mx-auto mb-3"
                  src="img/testimonial-4.jpg"
                  style={{ width: "80px", height: "80px" }}
                />
                <h5 className="mb-0">Trần Anh Tuấn</h5>
                <p>Profession</p>
                <div className="testimonial-text bg-light text-center p-4">
                  <p className="mb-0">
                    Khóa học này là một nguồn thông tin quý báu về Thiết kế Đồ
                    họa!. Nội dung của khóa học rất thú vị và được trình bày một
                    cách dễ hiểu, giúp tôi tiếp cận những khái niệm khó khăn một
                    cách dễ dàng. Tôi đã học được nhiều kỹ thuật mới và cảm thấy
                    tự tin hơn trong việc thực hiện các dự án thiết kế của mình.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div> */}
      </div>
    </ClientShared>
  );
};

export default Home;
