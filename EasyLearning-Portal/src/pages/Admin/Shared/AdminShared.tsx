import React, { ReactNode } from "react";
interface AdminSharedProps {
  children: ReactNode;
}
const AdminShared: React.FC<AdminSharedProps> = ({ children }) => {
  return (
    <div>
      <div id="wrapper">
        <ul
          className="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
          id="accordionSidebar"
        >
          <a
            className="sidebar-brand d-flex align-items-center justify-content-center"
            href="index.html"
          >
            <div className="sidebar-brand-icon rotate-n-15">
              <i className="fas fa-laugh-wink"></i>
            </div>
            <div className="sidebar-brand-text mx-3">Bảng điều khiển</div>
          </a>

          <hr className="sidebar-divider my-0" />

          <li className="nav-item active">
            <a className="nav-link" href="index.html">
              <i className="fas fa-fw fa-tachometer-alt"></i>
              <span>Quản trị viên</span>
            </a>
          </li>

          <hr className="sidebar-divider" />

          <div className="sidebar-heading">Quản lý khóa học</div>

          <li className="nav-item">
            <a
              className="nav-link collapsed"
              href="#"
              data-toggle="collapse"
              data-target="#collapseTwo"
              aria-expanded="true"
              aria-controls="collapseTwo"
            >
              <i className="fas fa-fw fa-cog"></i>
              <span>Khóa học</span>
            </a>
            <div
              id="collapseTwo"
              className="collapse"
              aria-labelledby="headingTwo"
              data-parent="#accordionSidebar"
            >
              <div className="bg-white py-2 collapse-inner rounded">
                <h6 className="collapse-header">Tùy chọn</h6>
                <a
                  className="collapse-item"
                  asp-action="Index"
                  asp-controller="course"
                  asp-area="admin"
                >
                  Danh sách
                </a>
                <a
                  className="collapse-item"
                  asp-action="Create"
                  asp-controller="course"
                  asp-area="admin"
                >
                  Thêm
                </a>
              </div>
            </div>
          </li>

          <li className="nav-item">
            <a
              className="nav-link collapsed"
              href="#"
              data-toggle="collapse"
              data-target="#collapseUtilities"
              aria-expanded="true"
              aria-controls="collapseUtilities"
            >
              <i className="fas fa-fw fa-wrench"></i>
              <span>Thể loại</span>
            </a>
            <div
              id="collapseUtilities"
              className="collapse"
              aria-labelledby="headingUtilities"
              data-parent="#accordionSidebar"
            >
              <div className="bg-white py-2 collapse-inner rounded">
                <h6 className="collapse-header">Tùy chọn</h6>
                <a
                  className="collapse-item"
                  asp-action="Index"
                  asp-controller="category"
                  asp-area="admin"
                >
                  Danh sách
                </a>
                <a
                  className="collapse-item"
                  asp-action="Create"
                  asp-controller="category"
                  asp-area="admin"
                >
                  Thêm
                </a>
              </div>
            </div>
          </li>

          <hr className="sidebar-divider" />

          <div className="sidebar-heading">Quản lý người dùng</div>

          <li className="nav-item">
            <a
              className="nav-link collapsed"
              href="#"
              data-toggle="collapse"
              data-target="#collapsePages"
              aria-expanded="true"
              aria-controls="collapsePages"
            >
              <i className="fas fa-fw fa-folder"></i>
              <span>Người dùng</span>
            </a>
            <div
              id="collapsePages"
              className="collapse"
              aria-labelledby="headingPages"
              data-parent="#accordionSidebar"
            >
              <div className="bg-white py-2 collapse-inner rounded">
                <h6 className="collapse-header">Tùy chọn</h6>
                <a
                  className="collapse-item"
                  asp-action="Index"
                  asp-controller="user"
                  asp-area="admin"
                >
                  Danh sách
                </a>
              </div>
            </div>
          </li>

          <li className="nav-item">
            <a className="nav-link" href="charts.html">
              <i className="fas fa-fw fa-chart-area"></i>
              <span>Doanh thu</span>
            </a>
          </li>

          <hr className="sidebar-divider d-none d-md-block" />
          <div className="sidebar-heading">Chức năng</div>
          <li className="nav-item">
            <a
              className="nav-link"
              asp-action="Recycle"
              asp-controller="course"
              asp-area="admin"
            >
              <i className="fas fa-trash-alt"></i>
              <span>thùng rác</span>
            </a>
          </li>
          <li className="nav-item">
            <a
              className="nav-link"
              asp-action="Index"
              asp-controller="Home"
              asp-area=""
            >
              <i className="fas fa-undo"></i>
              <span>Quay lại trang chính </span>
            </a>
          </li>
          <hr className="sidebar-divider d-none d-md-block" />
          <div className="text-center d-none d-md-inline">
            <button
              className="rounded-circle border-0"
              id="sidebarToggle"
            ></button>
          </div>
        </ul>
        <div id="content-wrapper" className="d-flex flex-column">
          <div className="main-container">{children}</div>
        </div>
      </div>
    </div>
  );
};

export default AdminShared;
