import React from "react";
import AuthenticationShared from "./Shared/AuthenticationShared";

const Register: React.FC = () => {
  return (
    <AuthenticationShared>
      <div className="container">
        <div className="logindiv">
          <div className="form-floating form-floating-outline mb-3">
            <input
              type="text"
              className="form-control"
              id="name"
              name="name"
              placeholder="Nhập Họ Và Tên"
            />
            <label style={{ color: "#06BBCC" }} htmlFor="name">
              Họ Và Tên
            </label>

            <span className="text-danger"></span>
          </div>

          <div className="form-floating form-floating-outline mb-3">
            <input
              type="text"
              className="form-control"
              id="email"
              name="email"
              placeholder="Nhập Email"
            />
            <label style={{ color: "#06BBCC" }} htmlFor="email">
              Email
            </label>

            <span className="text-danger"></span>
          </div>

          <div className="form-floating form-floating-outline mb-3">
            <input
              type="text"
              className="form-control"
              id="phonenumber"
              name="phonenumber"
              placeholder="Nhập Số Điện Thoại"
            />
            <label style={{ color: "#06BBCC" }} htmlFor="phonenumber">
              Số Điện Thoại
            </label>

            <span className="text-danger"></span>
          </div>
          <div className="form-floating form-floating-outline mb-3">
            <input
              type="text"
              className="form-control"
              id="address"
              name="address"
              placeholder="Nhập Địa Chỉ"
            />
            <label style={{ color: "#06BBCC" }} htmlFor="address">
              Địa Chỉ
            </label>

            <span className="text-danger"></span>
          </div>

          <div className="form-floating form-floating-outline mb-3">
            <input
              type="date"
              className="form-control"
              id="birthDate"
              name="birthDate"
              placeholder="Nhập Ngày Sinh"
            />
            <label style={{ color: "#06BBCC" }} htmlFor="birthDate">
              Ngày Sinh
            </label>

            <span className="text-danger"></span>
          </div>
          <div className="form-floating form-floating-outline mb-3">
            <input
              type="password"
              id="password"
              className="form-control"
              name="password"
              placeholder="&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;"
              aria-describedby="password"
            />
            <label style={{ color: "#06BBCC" }} htmlFor="password">
              Mật Khẩu
            </label>

            <span className="text-danger"></span>
          </div>

          <div className="form-floating form-floating-outline mb-3">
            <input
              type="password"
              id="confirmpassword"
              className="form-control"
              name="confirmpassword"
              placeholder="&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;"
              aria-describedby="confirmpassword"
            />
            <label style={{ color: "#06BBCC" }} htmlFor="confirmpassword">
              Xác Nhận Mật Khẩu
            </label>

            <span className="text-danger"></span>
          </div>

          <div className="form-floating form-floating-outline mb-3">
            <input
              type="file"
              id="Avatar"
              className="form-control"
              name="Avatar"
              placeholder="&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;"
              aria-describedby="Avatar"
            />
            <label style={{ color: "#06BBCC" }} htmlFor="confirmpassword">
              Ảnh đại diện
            </label>
            <span className="text-danger"></span>
          </div>
          <div className="mb-3">
            <div className="form-check">
              <label className="form-check-label" htmlFor="terms-conditions">
                Tôi đồng ý với
                <a style={{ color: "#06BBCC" }} href="javascript:void(0);">
                  Chính sách bảo mật & Các điều khoản
                </a>
              </label>
            </div>
          </div>
          <button
            style={{ backgroundColor: "#06BBCC", borderColor: "#06BBCC" }}
            type="submit"
            className="btn btn-primary d-grid w-100"
          >
            Đăng Ký
          </button>
          <p className="text-center">
            <span>Đã Có Tài Khoản ?</span>
            <a>
              <span>Đăng Nhập</span>
            </a>
          </p>
        </div>
      </div>
    </AuthenticationShared>
  );
};

export default Register;
