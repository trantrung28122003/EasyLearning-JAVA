import React from "react";
import AuthenticationShared from "./Shared/AuthenticationShared";
import LoginRequest from "../../../model/Authentication";
import { DoCallAPIWithOutToken } from "../../../services/HttpService";
import { URL_AUTHEN_LOGIN } from "../../../constants/API";
import * as yup from "yup";
import { ErrorMessage, Field, Form, Formik } from "formik";
const Login: React.FC = () => {
  const schema = yup.object().shape({
    userName: yup
      .string()
      .min(8, "Must be 8 characters")
      .max(24, "Maxinum be 24 characters")
      .required("UserName is required"),
    password: yup.string().required("Password is required"),
  });

  const doLogin = (user: LoginRequest) => {
    DoCallAPIWithOutToken(URL_AUTHEN_LOGIN, user, "post").then((res) => {
      console.log(res);
    });
  };

  return (
    <AuthenticationShared>
      <Formik
        initialValues={{ userName: "", password: "" }}
        validationSchema={schema}
        onSubmit={(values: LoginRequest) => {
          console.log("Form submitted:", values);
          doLogin(values);
        }}
        validateOnChange
      >
        {({ touched, errors }) => (
          <div className="container">
            <div className="form-floating form-floating-outline mb-3">
              <Form>
                <div className="form-floating form-floating-outline mb-3">
                  <Field
                    name="userName"
                    className="form-control"
                    type="text"
                    style={{ borderColor: "#06BBCC" }}
                    id="userName"
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
                    style={{ borderColor: "#06BBCC" }}
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
                <button
                  style={{
                    backgroundColor: "#06BBCC",
                    borderColor: "#06BBCC",
                  }}
                  type="submit"
                  className="btn btn-primary d-grid w-100"
                >
                  Đăng Nhập
                </button>
                <p className="text-center" style={{ marginTop: "10px" }}>
                  <a style={{ textDecoration: "underline", color: "#06BBCC" }}>
                    Bạn quên mật khẩu?
                  </a>
                </p>

                <p className="text-center">
                  <span>Chưa có tài khoản ? </span>
                  <a>
                    <button
                      style={{
                        backgroundColor: "#06BBCC",
                        padding: "0.5rem",
                        border: "none",
                      }}
                    >
                      Đăng ký{" "}
                    </button>{" "}
                  </a>
                </p>

                <a
                  style={{ backgroundColor: "#06BBCC", borderColor: "#06BBCC" }}
                  className="btn btn-primary d-grid w-100"
                >
                  Log In With Google
                </a>
              </Form>
            </div>
          </div>
        )}
      </Formik>
    </AuthenticationShared>
  );
};

export default Login;
