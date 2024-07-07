import React from "react";
import AuthenticationShared from "./Shared/AuthenticationShared";
import { useNavigate } from "react-router-dom";
import * as yup from "yup";
import { RegisterRequest } from "../../../model/Authentication";
import { Field, Form, Formik } from "formik";
import { getToday } from "../../../hooks/useTime";
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
    imageName: yup.string().required("Avatar is required"),
    password: yup.string().required("Password is required"),
    confirmPassword: yup
      .string()
      .oneOf([yup.ref("password"), ""], "Passwords must match"),
    termAndConditions: yup
      .boolean()
      .oneOf([true], "You must accept the terms and conditions"),
  });

  const doRegister = (account: RegisterRequest) => {
    navigate("/login");
  };

  return (
    <AuthenticationShared>
      <Formik
        initialValues={{
          userName: "",
          email: "",
          fullName: "",
          dayOfBirth: new Date(getToday()),
          imageName: "",
          imageUrl: null,
          password: "",
          termAndConditions: false,
          confirmPassword: "",
        }}
        validationSchema={schema}
        onSubmit={(values: RegisterRequest) => {
          //console.log(values);
          doRegister(values);
        }}
        validateOnChange
      >
        {({ touched, errors, setFieldValue, handleChange }) => (
          <div className="container">
            <Form>
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

                {errors.password && touched.password ? (
                  <div className="text-danger">{errors.password}</div>
                ) : null}
              </div>

              <div className="form-floating form-floating-outline mb-3">
                <Field
                  type="password"
                  id="confirmPassword"
                  className="form-control"
                  name="confirmPassword"
                  placeholder="&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;"
                  aria-describedby="confirmPassword"
                />
                <label style={{ color: "#06BBCC" }} htmlFor="confirmPassword">
                  Xác Nhận Mật Khẩu
                </label>

                {errors.confirmPassword && touched.confirmPassword ? (
                  <div className="text-danger">{errors.confirmPassword}</div>
                ) : null}
              </div>

              <div className="form-floating form-floating-outline mb-3">
                <input
                  className="form-control"
                  type="file"
                  id="imageName"
                  onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                    if (event.currentTarget.files) {
                      console.log(event.currentTarget.files[0].name);
                      setFieldValue(
                        "imageName",
                        event.currentTarget.files[0].name
                      );
                      setFieldValue("imageUrl", event.currentTarget.files[0]);
                    }
                  }}
                />
                <label style={{ color: "#06BBCC" }} htmlFor="imageName">
                  Ảnh đại diện
                </label>
                {errors.imageName && touched.imageName ? (
                  <div className="text-danger">{errors.imageName}</div>
                ) : null}
              </div>

              <div className="mb-3">
                <input
                  type="checkbox"
                  id="termAndConditions"
                  name="termAndConditions"
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="termAndConditions">
                  &nbsp;Tôi đồng ý với {"  "}
                  <a style={{ color: "#06BBCC" }}>
                    Chính sách bảo mật & Các điều khoản
                  </a>
                </label>
                {errors.termAndConditions && touched.termAndConditions ? (
                  <div className="text-danger">{errors.termAndConditions}</div>
                ) : null}
              </div>
              <button
                style={{
                  backgroundColor: "#06BBCC",
                  borderColor: "#06BBCC",
                }}
                type="submit"
                className="btn btn-primary d-grid w-100"
              >
                Đăng Ký
              </button>
            </Form>
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
