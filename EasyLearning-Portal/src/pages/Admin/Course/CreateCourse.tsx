import React from "react";
import AdminShared from "../Shared/AdminShared";

const CreateCourse: React.FC = () => {
  return (
    <>
      <AdminShared>
        <h1 className="text-center ">Thêm khóa học mới</h1>
        <hr />
        <div className="container">
          <form method="post" encType="multipart/form-data">
            <div className="text-danger"></div>
            <div className="form-group">
              <label className="control-label">Tên khóa học</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label>Hình thức:</label>
              <select className="form-control">
                <option value="">Online</option>
                <option value="">Offline</option>
              </select>
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label"> Mô tả chi tiết</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label">Giá</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label">Yêu cầu</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label">Nội dung</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>

            <div className="form-group">
              <label className="control-label">Ngày bắt đầu </label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label">Ngày kết thúc</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label">Hạn đăng ký</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>

            <div className="form-group" id="instructorFormGroup">
              <label className="control-label">Giảng viên</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label"></label>
              <input type="checkbox" id="toggleInstructor" />
              <span>
                Click vào đây sẽ lấy tên trong hồ sơ tài khoản của bạn!
              </span>
            </div>

            <div className="form-group">
              <label className="control-label">Số học viên tối đa</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label">Hình ảnh</label>
              <div className="custom-file">
                <input
                  className="custom-file-input"
                  type="file"
                  id="customFile"
                  accept="image/*"
                />
                <label className="custom-file-label" htmlFor="customFile">
                  Chọn tệp
                </label>
              </div>
              <span className="text-danger"></span>
            </div>

            <div className="form-group">
              <label>Thể Loại:</label>
              <div className="checkbox">
                <label>
                  <input
                    type="checkbox"
                    name="selectedCategories"
                    value="@category.Value"
                  />
                  Thể loại
                </label>
              </div>
            </div>

            <div className="form-group text-center">
              <input
                type="submit"
                value="Thêm khóa học"
                className="btn btn-outline-info btn-lg btn-block"
              />
            </div>
          </form>
        </div>

        <div>
          <a className="btn btn-outline-danger">Quay lại</a>
        </div>
      </AdminShared>
    </>
  );
};

export default CreateCourse;
