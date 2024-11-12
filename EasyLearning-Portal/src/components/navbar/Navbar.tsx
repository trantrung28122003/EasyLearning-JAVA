import React from "react";
import { hasAdminRole, isUserLogin } from "../../hooks/useLogin";
import { useNavigate } from "react-router-dom";

const Navbar: React.FC = () => {
  const isAdmin = hasAdminRole();
  const isLogin = isUserLogin();
  const navigator = useNavigate();
  const handleLogout = () => {
    localStorage.clear();
    navigator("/");
  };
  const handleLogin = () => {
    localStorage.clear();
    navigator("/login");
  };
  return (
    <>
      <nav
        className="navbar navbar-expand-lg bg-white navbar-light shadow sticky-top p-0"
        // style={{ marginBottom: "3rem" }}
      >
        <a
          href="/"
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
            <a href="/" className="nav-item nav-link active">
              Trang Chủ
            </a>
            <a href="/about" className="nav-item nav-link">
              Thông Tin Về Chúng Tôi
            </a>
            <a className="nav-item nav-link" href="/courses">
              Các Khóa Học
            </a>
            {isLogin && (
              <>
                <div className="nav-item dropdown">
                  <a
                    href="#"
                    className="nav-link dropdown-toggle"
                    data-bs-toggle="dropdown"
                  >
                    Mục Lục
                  </a>
                  <div className="dropdown-menu fade-down m-0">
                    <a className="dropdown-item" href="/userCourses">
                      Danh sách khóa học của bạn
                    </a>
                    <a className="dropdown-item">Cài đặt tài khoản</a>
                    {isAdmin && (
                      <a className="dropdown-item" href="admin/dashboard">
                        Quản lý
                      </a>
                    )}
                  </div>
                </div>
                <a
                  className="position-relative me-4 my-auto"
                  href="/shoppingCart"
                >
                  <i className="fa fa-shopping-bag fa-2x"></i>
                </a>
              </>
            )}
            {isLogin ? (
              <button
                className="btn btn-primary py-4 px-lg-5 d-none d-lg-block"
                onClick={handleLogout}
              >
                Đăng Xuất<i className="fa fa-arrow-right ms-3"></i>
              </button>
            ) : (
              <button
                className="btn btn-primary py-4 px-lg-5 d-none d-lg-block"
                onClick={handleLogin}
              >
                Tham Gia Ngay<i className="fa fa-arrow-right ms-3"></i>
              </button>
            )}
          </div>
        </div>
      </nav>
    </>
  );
};
export default Navbar;
