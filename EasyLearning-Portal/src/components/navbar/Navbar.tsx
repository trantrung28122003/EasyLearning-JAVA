import React, { useEffect, useRef, useState } from "react";
import { getUserInfo, hasAdminRole, isUserLogin } from "../../hooks/useLogin";
import { useNavigate } from "react-router-dom";
import "./Navbar.css";
import {
  GET_NOTIFICATION_BY_USER,
  UPDATE_NOTIFICATION_READ_STATUS,
} from "../../constants/API";
import { DoCallAPIWithToken } from "../../services/HttpService";
import { HTTP_OK } from "../../constants/HTTPCode";
import { getTimeAgo } from "../../hooks/useTime";
import { getWebSocketClient } from "../../hooks/websocket";

interface Notification {
  id: string;
  contentNotification: string;
  dateCreate: string;
  isRead: boolean;
  type: string;
  targetId: string;
}

const Navbar: React.FC = () => {
  const isAdmin = hasAdminRole();
  const isLogin = isUserLogin();
  const navigator = useNavigate();
  const [isNotificationOpen, setIsNotificationOpen] = useState(false);
  const notificationsRef = useRef<HTMLDivElement>(null);
  const bellIconRef = useRef<HTMLDivElement>(null);
  const [notifications, setNotifications] = useState<Notification[]>([]);

  const FetchNotificationByUser = async () => {
    try {
      const URL = GET_NOTIFICATION_BY_USER;
      const response = await DoCallAPIWithToken(URL, "GET");
      if (response.status === HTTP_OK) {
        setNotifications(response.data.result);
      }
    } catch (error) {}
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

  const client = getWebSocketClient();
  useEffect(() => {
    if (isLogin) {
      FetchNotificationByUser();
    }
    const currentUser = getUserInfo();
    const handleClickOutside = (event: MouseEvent) => {
      if (
        notificationsRef.current &&
        !notificationsRef.current.contains(event.target as Node) &&
        !bellIconRef.current?.contains(event.target as Node)
      ) {
        setIsNotificationOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    client.onConnect = () => {
      client.subscribe("/topic/notifications", (content) => {
        const newNotification = JSON.parse(content.body);
        setNotifications((prevNotifications) => [
          ...prevNotifications,
          newNotification,
        ]);
      });
      if (currentUser?.id) {
        client.subscribe(`/user/${currentUser.id}/notifications`, (content) => {
          const newNotification = JSON.parse(content.body);
          setNotifications((prevNotifications) => [
            ...prevNotifications,
            newNotification,
          ]);
        });
      }
    };
    client.activate();

    return () => {
      if (client.active) client.deactivate();
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [client]);

  const unreadNotificationsCount = notifications.filter(
    (notification) => !notification.isRead
  ).length;

  const getNotificationIcon = (type: string) => {
    switch (type) {
      case "COMMENT":
        return <i className="fa fa-comments"></i>; // Ví dụ: icon cho bình luận
      case "CERTIFICATE":
        return <i className="fa fa-certificate"></i>; // Ví dụ: icon cho chứng chỉ
      case "COURSE_PURCHASE":
        return <i className="fa fa-graduation-cap"></i>; // Ví dụ: icon cho khóa học
      default:
        return <i className="fa fa-bell"></i>; // Icon mặc định
    }
  };
  const updateNotificationReadStatus = async (notificationId: string) => {
    try {
      const URL = `${UPDATE_NOTIFICATION_READ_STATUS}?notificationId=${notificationId}`;
      const response = await DoCallAPIWithToken(URL, "POST");
      if (response.status === HTTP_OK) {
      }
    } catch (error) {}
  };

  const handleMarkAllAsRead = async () => {
    notifications.map(async (notification) => {
      if (!notification.isRead) {
        updateNotificationReadStatus(notification.id);
      }
    });

    setNotifications((prevNotifications) =>
      prevNotifications.map((notification) => ({
        ...notification,
        isRead: true,
      }))
    );
  };

  const handleNotificationClick = (notification: Notification) => {
    if (!notification.isRead) {
      updateNotificationReadStatus(notification.id);
      const notificationId = notification.id;
      setNotifications((prevNotifications) =>
        prevNotifications.map((notification) =>
          notification.id === notificationId
            ? { ...notification, isRead: true }
            : notification
        )
      );
    }
    if (notification.targetId != null) {
      navigator(`${notification.targetId}`);
    }
  };

  return (
    <>
      <nav className="navbar navbar-expand-lg bg-white navbar-light shadow sticky-top p-0">
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
                    <a className="dropdown-item" href="/certificate">
                      Chứng chỉ của bạn
                    </a>

                    <a className="dropdown-item" href="/userProfile">
                      Cài đặt tài khoản
                    </a>
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
                <div
                  className="position-relative me-4 my-auto"
                  ref={bellIconRef}
                >
                  <i
                    className="fa fa-bell fa-2x"
                    onClick={() => setIsNotificationOpen(!isNotificationOpen)}
                    style={{ cursor: "pointer", fontSize: "30px" }}
                  >
                    {unreadNotificationsCount > 0 && (
                      <span className="notification-badge">
                        {unreadNotificationsCount}
                      </span>
                    )}
                  </i>
                  {isNotificationOpen && (
                    <div
                      className="notification-dropdown"
                      ref={notificationsRef}
                    >
                      <div className="notification-header">
                        <div style={{ fontSize: "20px" }}>Thông báo</div>
                        <button
                          className="mark-read"
                          onClick={handleMarkAllAsRead}
                        >
                          Đánh dấu tất cả đã đọc
                        </button>
                      </div>
                      <div className="notification-list">
                        {notifications.map((notification, index) => (
                          <div
                            key={index}
                            className={`notification-item ${
                              notification.isRead ? "read" : ""
                            }`}
                            onClick={() =>
                              handleNotificationClick(notification)
                            }
                          >
                            <div className="notification-content">
                              {/* Hiển thị icon tùy thuộc vào loại thông báo */}
                              <div className="notification-icon">
                                {getNotificationIcon(notification.type)}
                              </div>
                              <div className="notification-text">
                                <p>{notification.contentNotification}</p>
                                <span>
                                  {getTimeAgo(notification.dateCreate)}
                                </span>
                                {!notification.isRead && (
                                  <span className="new-notification-dot"></span>
                                )}
                              </div>
                            </div>
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
