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
import { GET_COURSES_MOST_REGISTERED, GET_TOP_FOUR_MOST_CATEGORY } from "../../../constants/API";
import { ApplicationResponse } from "../../../model/BaseResponse";
import Card from "../../../components/card/Card";
import { Course } from "../../../model/Course";
import { Feedback } from "../../../model/FeedBack";
import { Category, CategoryWithCourse } from "../../../model/Category";
const Home: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const doGetCourses = () => {
    DoCallAPIWithOutToken(GET_COURSES_MOST_REGISTERED, "get").then((res) => {
      if (res.status === HTTP_OK) {
        const data: ApplicationResponse<Course[]> = res.data;
        setCourses(data.result);
      }
    });
  };

  const doGetTopMostCategory = () => {
    DoCallAPIWithOutToken(GET_TOP_FOUR_MOST_CATEGORY, "get").then(response =>{
      if(response.status=== HTTP_OK){
          const data: ApplicationResponse<Category[]> = response.data;
          setCategories(data.result);
         
      }
    });
  };

  const feedbacks: Feedback[] = [];
  courses.forEach((course) => {
    feedbacks.concat(course.feedbacks);
  });

  useEffect(() => {
    doGetCourses();
    doGetTopMostCategory();

  }, []);

  return (
    <ClientShared>
      <div className={styled.home}>

        <div className="container-fluid p-0 mb-5">
            <div className="header-carousel position-relative">
                <div className="owl-carousel-item position-relative">
                    <img className="img-fluid" src="https://easylearning.blob.core.windows.net/images-videos/carousel.jpg" alt="" style ={{width: "100%"}}/>
                    <div className="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center" style={{background: "rgba(24, 29, 56, .7)"}}>
                        <div className="container">
                            <div className="row justify-content-start">
                                <div className="col-sm-10 col-lg-8">
                                    <h5 className="text-primary text-uppercase mb-3 animated slideInDown">Các Khóa Học Tốt Nhất</h5>
                                    <h1 className="display-3 text-white animated slideInDown">Nền Tảng Bán Khóa Học Tốt Nhất Hiện Nay</h1>
                                    <a asp-controller="About" asp-action="IndexAbout" className="btn btn-primary py-md-3 px-md-5 me-3 animated slideInLeft">Xem Thêm</a>
                                    <a asp-controller="CustomerCourses" asp-action="Listcourse" className="btn btn-light py-md-3 px-md-5 animated slideInRight">Tham Gia Ngay</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

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
                      Phơng pháp giảng dạy linh hoạt và có kinh nghiệm trong lĩnh vực chuyên môn
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
                    Thuận tiện cho việc đăng ký, lựa chọn thời gian và lịch học phù hợp với lịch trình cá nhân. 
                    Bạn có thể theo dõi và quản lý các khóa học đã đăng ký một cách dễ dàng. 
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
                    Không bị giới hạn bởi môi trường làm việc truyền thống, có thể chọn thời gian làm việc phù hợp với lịch trình cá nhân và tạo điều kiện tốt nhất để sáng tạo. 
                    Hãy phát huy tối đa khả năng của bạn từ không gian quen thuộc!
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
                    Cung cấp một loạt các tài liệu và tài nguyên khác nhau, từ sách, bài báo, đến bản ghi và hướng dẫn, giúp bạn nắm vững thông tin cần thiết cho dự án của mình. 
                    Chúng tôi cam kết hỗ trợ bạn trong suốt quá trình học tập và phát triển bền vững, tạo ra giá trị thiết thực và lâu dài!
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
              <h1 className="mb-5">Các Danh Mục Có Nhiều Khóa Học</h1>
            </div>
            <div className="row g-3">
              <div className="col-lg-7 col-md-6">
                <div className="row g-3">
                  {categories[0] &&(
                    <div
                      className="col-lg-12 col-md-12 wow zoomIn"
                      data-wow-delay="0.1s"
                    >
                      <a
                        className="position-relative d-block overflow-hidden"
                        href={"/courses/" + categories[0].id}
                      >
                        <img className="img-fluid" src={Cat1Img} alt="" />
                        <div
                          className="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3"
                          style={{ margin: "1px" }}
                        >
                          <h5 className="m-0">{categories[0].categoryName}</h5>
                          <small className="text-primary">{categories[0].coursesDetails?.length || 0 } Khóa học</small>
                        </div>
                      </a>
                    </div>
                  )}
                {categories[1] &&(
                  <div
                    className="col-lg-6 col-md-12 wow zoomIn"
                    data-wow-delay="0.3s"
                  >
                    <a
                      className="position-relative d-block overflow-hidden"
                      href={"/courses/" + categories[1].id}
                    >
                      <img className="img-fluid" src={Cat2Img} alt="" />
                      <div
                        className="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3"
                        style={{ margin: "1px" }}
                      >
                        <h5 className="m-0">{categories[1].categoryName}</h5>
                        <small className="text-primary">{categories[1].coursesDetails?.length || 0 } Khóa học</small>
                      </div>
                    </a>
                  </div>
                  )}
                  {categories[2] &&(
                  <div
                    className="col-lg-6 col-md-12 wow zoomIn"
                    data-wow-delay="0.5s"
                  >
                    <a
                      className="position-relative d-block overflow-hidden"
                      href={"/courses/" + categories[2].id}
                    >
                      <img className="img-fluid" src={Cat3Img} alt="" />
                      <div
                        className="bg-white text-center position-absolute bottom-0 end-0 py-2 px-3"
                        style={{ margin: "1px" }}
                      >
                        <h5 className="m-0">{categories[2].categoryName}</h5>
                        <small className="text-primary">{categories[2].coursesDetails?.length || 0 } Khóa học</small>
                      </div>
                    </a>
                  </div>
                  )}
                </div>
              </div>
              {categories[3] &&(
              <div
                className="col-lg-5 col-md-6 wow zoomIn"
                data-wow-delay="0.7s"
                style={{ minHeight: "350px" }}
              >
                <a
                  className="position-relative d-block h-100 overflow-hidden"
                  href={"/courses/" + categories[3].id}
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
                    <h5 className="m-0">{categories[3].categoryName}</h5>
                    <small className="text-primary">{categories[3].coursesDetails?.length || 0 } Khóa học</small>
                  </div>
                </a>
              </div>
              )}
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
                  <div
                    className="overflow-hidden"
                    style={{ display: "flex", justifyContent: "center" }}
                  >
                    <img
                      className="img-fluid"
                      src={
                        "https://easylearning.blob.core.windows.net/images-videos/user1.jpgea0c0be2-11c0-4948-b908-fcfa615b7835"
                      }
                    
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
                    <h5 className="mb-0">Bùi Phú Khuyên</h5>
                  
                  </div>
                </div>
              </div>
              <div
                className="col-lg-3 col-md-6 wow fadeInUp"
                data-wow-delay="0.3s"
              >
                <div className="team-item bg-light">
                  <div
                    className="overflow-hidden"
                    style={{ display: "flex", justifyContent: "center" }}
                  >
                    <img
                      className="img-fluid"
                      src={
                        "https://easylearning.blob.core.windows.net/images-videos/user2.jpg"
                      }
                    
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
                    <h5 className="mb-0">Nguyễn Vũ Hoàng Hiệp</h5>
                  
                  </div>
                </div>
              </div>
              <div
                className="col-lg-3 col-md-6 wow fadeInUp"
                data-wow-delay="0.5s"
              >
                <div className="team-item bg-light">
                  <div
                    className="overflow-hidden"
                    style={{ display: "flex", justifyContent: "center" }}
                  >
                    <img
                      className="img-fluid"
                      src={
                        "https://easylearning.blob.core.windows.net/images-videos/userDefault.jpg81716900-b5dd-468a-97eb-ca2678f03288"
                      }
                      style={{ height: "10rem", width: "7.5rem" }}
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
                    <h5 className="mb-0">Đặng Ngọc Sơn</h5>
                
                  </div>
                </div>
              </div>
              <div
                className="col-lg-3 col-md-6 wow fadeInUp"
                data-wow-delay="0.7s"
              >
                <div className="team-item bg-light">
                  <div
                    className="overflow-hidden"
                    style={{ display: "flex", justifyContent: "center" }}
                  >
                    <img
                      className="img-fluid"
                      src={
                        "https://easylearning.blob.core.windows.net/images-videos/user1.jpgea0c0be2-11c0-4948-b908-fcfa615b7835"
                      }
                    
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
                    <h5 className="mb-0">Đặng Ngọc Sơn</h5>
            
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </ClientShared>
  );
};

export default Home;
