import React from "react";
import AuthenticationShared from "./Shared/AuthenticationShared";
import { useNavigate } from "react-router-dom";
import * as yup from "yup";
import { RegisterRequest } from "../../../model/Authentication";
import { Field, Formik } from "formik";
const Register: React.FC = () => {
  const navigate = useNavigate();
  const schema = yup.object().shape({
    userName: yup
      .string()
      .min(8, "Must be 8 characters")
      .max(24, "Maxinum be 24 characters")
      .required("UserName is required"),
    email: yup
      .string()
      .email("Must be an email")
      .required("Day of birth is required"),
    fullName: yup
      .string()
      .required("Full name is required")
      .max(50, "Maximun be 50 characters"),
    dayOfBirth: yup.date().required("Day of birth is required"),
    imageUrl: yup.mixed().required("Avatar mus have"),
    password: yup.string().required("Password is required"),
  });

  return (
    <AuthenticationShared>
      <Formik
        initialValues={{
          userName: "",
          email: "",
          fullName: "",
          dayOfBirth: null,
          imageUrl: null,
          password: "",
        }}
        validationSchema={schema}
        onSubmit={(values: RegisterRequest) => {
          console.log(values);
        }}
        validateOnChange
      >
        {({ touched, errors }) => (
          <div className="container">
            <div className="form-floating form-floating-outline mb-3">
              <Field
                type="text"
                className="form-control"
                id="userName"
                name="userName"
                placeholder="Nhập UserName"
              />
              <label style={{ color: "#06BBCC" }} htmlFor="userName">
                UserName
              </label>
              {errors.userName && touched.userName ? (
                <div className="text-danger">{errors.userName}</div>
              ) : null}
            </div>

            <div className="form-floating form-floating-outline mb-3">
              <Field
                type="text"
                className="form-control"
                id="fullName"
                name="fullName"
                placeholder="Nhập Họ Và Tên"
              />
              <label style={{ color: "#06BBCC" }} htmlFor="fullName">
                Họ Và Tên
              </label>
              {errors.fullName && touched.fullName ? (
                <div className="text-danger">{errors.fullName}</div>
              ) : null}
            </div>

            <div className="form-floating form-floating-outline mb-3">
              <Field
                type="text"
                className="form-control"
                id="email"
                name="email"
                placeholder="Nhập Email"
              />
              <label style={{ color: "#06BBCC" }} htmlFor="email">
                Email
              </label>

              {errors.email && touched.email ? (
                <div className="text-danger">{errors.email}</div>
              ) : null}
            </div>

            <div className="form-floating form-floating-outline mb-3">
              <Field
                type="date"
                className="form-control"
                id="dayOfBirth"
                name="dayOfBirth"
                placeholder="Nhập Ngày Sinh"
              />
              <label style={{ color: "#06BBCC" }} htmlFor="dayOfBirth">
                Ngày Sinh
              </label>

              {errors.dayOfBirth && touched.dayOfBirth ? (
                <div className="text-danger">{errors.dayOfBirth}</div>
              ) : null}
            </div>

            <div className="form-floating form-floating-outline mb-3">
              <Field
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

              {errors.dayOfBirth && touched.dayOfBirth ? (
                <div className="text-danger">{errors.dayOfBirth}</div>
              ) : null}
            </div>

            <div className="form-floating form-floating-outline mb-3">
              <Field
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
              <Field
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
                  Tôi đồng ý với {"  "}
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
          </div>
        )}
      </Formik>
      <p className="text-center">
        <span>Đã Có Tài Khoản ?</span>
        <a>
          <span>Đăng Nhập</span>
        </a>
      </p>
    </AuthenticationShared>
  );
};

export default Register;
