import React from "react";

const Navbar: React.FC = () => {
  return (
    <>
      <nav
        className="navbar navbar-expand-lg bg-white navbar-light shadow sticky-top p-0"
        style={{ marginBottom: "3rem" }}
      >
        <a
          href="index.html"
          className="navbar-brand d-flex align-items-center px-4 px-lg-5"
        >
          <h2 className="m-0 text-primary">
            <i className="fa fa-book me-3"></i>eLEARNING
          </h2>
        </a>
        <button
          type="button"
          className="navbar-toggler me-4"
          data-bs-toggle="collapse"
          data-bs-target="#navbarCollapse"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarCollapse">
          <div className="navbar-nav ms-auto p-4 p-lg-0">
            <a
              asp-controller="Home"
              asp-action="Index"
              className="nav-item nav-link active"
            >
              Trang Chủ
            </a>
            <a
              asp-controller="About"
              asp-action="IndexAbout"
              className="nav-item nav-link"
            >
              Thông Tin Về Chúng Tôi
            </a>
            <a
              asp-controller="CustomerCourses"
              asp-action="ListCourse"
              className="nav-item nav-link"
            >
              Các Khóa Học
            </a>
            <div className="nav-item dropdown">
              <a
                href="#"
                className="nav-link dropdown-toggle"
                data-bs-toggle="dropdown"
              >
                Mục Lục
              </a>
              <div className="dropdown-menu fade-down m-0">
                <a
                  asp-controller="CustomerCourses"
                  asp-action="EventSchedule"
                  className="dropdown-item"
                >
                  Thời khóa biểu
                </a>
                <a
                  asp-controller="CustomerCourses"
                  asp-action="ListCourseOnlineByUser"
                  className="dropdown-item"
                >
                  Danh sách khóa học của bạn
                </a>
                <a
                  asp-controller="account"
                  asp-action="login"
                  className="dropdown-item"
                >
                  Cài đặt tài khoản
                </a>
                <a className="dropdown-item">Quản lý</a>
              </div>
            </div>
            <a
              asp-controller="ShoppingCart"
              asp-action="GetShoppingCart"
              className="position-relative me-4 my-auto"
            >
              <i className="fa fa-shopping-bag fa-2x"></i>
            </a>
          </div>
        </div>
      </nav>
    </>
  );
};
export default Navbar;
