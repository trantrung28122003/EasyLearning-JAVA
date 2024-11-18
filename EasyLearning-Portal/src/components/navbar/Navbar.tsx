import React, { useEffect, useRef, useState } from "react";
import { hasAdminRole, isUserLogin } from "../../hooks/useLogin";
import { useNavigate } from "react-router-dom";
import "./test.css";
const Navbar: React.FC = () => {
  const isAdmin = hasAdminRole();
  const isLogin = isUserLogin();
  const navigator = useNavigate();
  const [isNotificationOpen, setNotificationOpen] = useState(false);
  const notificationsRef = useRef<HTMLDivElement>(null);
  const [notifications, setNotifications] = useState([
    {
      id: 1,
      message: "Bài học Tham gia cộng đồng F8 trên Discord mới được thêm vào.",
      time: "8 tháng trước",
      isRead: false,
    },
    {
      id: 2,
      message: "Bài học Mua áo F8 | Đăng ký học Offline mới được thêm vào.",
      time: "1 năm trước",
      isRead: false,
    },
    {
      id: 3,
      message: "Bài học CSS selectors cơ bản #1 mới được thêm vào.",
      time: "2 năm trước",
      isRead: false,
    },
  ]);

  const unreadCount = notifications.filter((notif) => !notif.isRead).length;

  // Đánh dấu tất cả là đã đọc
  const markAllAsRead = () => {
    setNotifications((prev) =>
      prev.map((notif) => ({ ...notif, isRead: true }))
    );
  };

  // Đánh dấu một thông báo là đã đọc
  const markAsRead = (id: number) => {
    setNotifications((prev) =>
      prev.map((notif) =>
        notif.id === id ? { ...notif, isRead: true } : notif
      )
    );
  };

  const handleLogout = () => {
    localStorage.clear();
    window.location.reload();
    navigator("/");
  };
  const handleLogin = () => {
    localStorage.clear();
    navigator("/login");
  };

  // Đóng thông báo khi nhấn ngoài
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      // Kiểm tra nếu click không thuộc vào khung thông báo
      if (
        notificationsRef.current &&
        !notificationsRef.current.contains(event.target as Node)
      ) {
        setNotificationOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);
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
            <a className="nav-item nav-link" href="/courses/search">
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
                    <a className="dropdown-item" href="/schedule">
                      Thời khóa biểu
                    </a>
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

                {/* <a
                  className="position-relative me-4 my-auto"
                  href="/notifications"
                >
                  <i className="fa fa-bell fa-2x"></i>
                  <span className="notification-count">3</span>{" "}
                </a> */}
                <div className="position-relative me-4 my-auto">
                  <i
                    className="fa fa-bell fa-2x"
                    onClick={() => setNotificationOpen(!isNotificationOpen)}
                    ref={notificationsRef}
                    style={{ cursor: "pointer" }}
                  >
                    {unreadCount > 0 && (
                      <span className="notification-badge">{unreadCount}</span>
                    )}
                  </i>
                  {isNotificationOpen && (
                    <div
                      className="notification-dropdown"
                      ref={notificationsRef}
                    >
                      <div className="notification-header">
                        <h5>Thông báo</h5>
                        <button className="mark-read">Đánh dấu đã đọc</button>
                      </div>
                      <div className="notification-list">
                        {notifications.map((notif) => (
                          <div
                            key={notif.id}
                            className={`notification-item ${
                              notif.isRead ? "read" : ""
                            }`}
                            onClick={() => markAsRead(notif.id)} // Đánh dấu đã đọc khi nhấn vào
                          >
                            <p>{notif.message}</p>
                            <span>{notif.time}</span>
                          </div>
                        ))}
                      </div>
                    </div>
                  )}
                </div>
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
