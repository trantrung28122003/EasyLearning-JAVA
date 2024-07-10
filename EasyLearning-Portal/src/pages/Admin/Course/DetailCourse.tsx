import React from "react";
import AdminShared from "../Shared/AdminShared";

const DetailCourse: React.FC = () => {
  return (
    <AdminShared>
      <div>
        <h1 className="text-center">Thông tin chi tiết của khóa học</h1>
        <h1 className="text-center" style={{ marginBottom: "40px" }}>
          <strong></strong>{" "}
        </h1>
        <hr />
        <div className="container">
          <div className="form-group">
            <label className="control-label">Mã khóa học</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Tên khóa học</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Hình thức</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Chi tiết khóa học</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Học phí</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Yêu cầu</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Nội dung khóa học</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Đường dẫn hình ảnh</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Ngày bắt đầu</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Ngày kết thúc</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Hạn đăng ký</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Giảng viên</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Số học viên tối đa</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Ngày tạo</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Ngày thay đổi</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Thể loại</label>
            <div className="checkbox">
              <label>
                <input type="checkbox" name="selectedCategories" disabled />
              </label>
            </div>
          </div>
        </div>

        <div className="form-group text-center">
          <a className="btn btn-outline-info btn-lg btn-block">Quay lại</a>
        </div>
      </div>
    </AdminShared>
  );
};

export default DetailCourse;
