import React, { useState } from "react";
import AuthenticationShared from "./Shared/AuthenticationShared";
import {
  DoCallAPIWithOutToken,
  DoCallAPIWithToken,
} from "../../../services/HttpService";
import { GET_USER_INFO_URL, LOGIN_URL } from "../../../constants/API";
import * as yup from "yup";
import { Field, Form, Formik } from "formik";
import { useNavigate } from "react-router-dom";
import { getCredentials } from "../../../hooks/useLogin";
import { HTTP_OK } from "../../../constants/HTTPCode";
import { LoginRequest } from "../../../model/Authentication";
import { useGoogleLogin } from "@react-oauth/google";

const Login: React.FC = () => {
  const [loginError, setLoginError] = useState<string | null>(null); // State lưu lỗi đăng nhập
  const navigate = useNavigate();

  const schema = yup.object().shape({
    userName: yup
      .string()
      .min(8, "Tên ài khoản phải có ít nhất 8 ký tự")
      .max(24, "Tên tài khoản tối đa 24 ký tự")
      .required("Tên tài khoản không được để trống"),
    password: yup.string().required("Mật khẩu không được để trống"),
  });

  const doLogin = (user: LoginRequest) => {
    DoCallAPIWithOutToken<LoginRequest>(LOGIN_URL, "post", user)
      .then((res) => {
        if (res.status === HTTP_OK) {
          localStorage.setItem(
            "authentication",
            JSON.stringify(res.data.result)
          );
          fetchCurrentUser();
        } else {
          setLoginError("Tài khoản hoặc mật khẩu không đúng.");
        }
      })

      .catch((err) => {
        if (
          err.response &&
          (err.response.status === 404 || err.response.status === 500)
        ) {
          console.log(err.response.status);
          setLoginError("Tài khoản hoặc mật khẩu không đúng.");
        } else {
          setLoginError("Đăng nhập thất bại. Vui lòng thử lại sau.");
        }
      });
  };

  const googleLogin = useGoogleLogin({
    onSuccess: async (tokenResponse) => {
      console.log("Google Login Success:", tokenResponse);
      console.log("tokenResponse", tokenResponse.code);
      // Gửi mã token đến backend
      const response = await fetch(
        "http://localhost:8080/auth/outbound/authentication",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ code: tokenResponse.code }),
        }
      );
      const data = await response.json();

      if (data.token) {
        localStorage.setItem("authentication", data.token);
        window.location.href = "/"; // Chuyển hướng đến trang chính
      } else {
        console.error("Login failed");
      }
    },
    onError: () => {
      console.error("Google Login failed");
    },
    flow: "auth-code", // Sử dụng Authorization Code Flow
  });

  const fetchCurrentUser = () => {
    const token = getCredentials();
    DoCallAPIWithToken(GET_USER_INFO_URL, "get").then((res) => {
      if (res.status === HTTP_OK) {
        localStorage.setItem("user_info", JSON.stringify(res.data.result));
        navigate("/");
      } else {
        navigate("/login-fail");
      }
    });
  };

  const handleRegister = () => {
    localStorage.clear();
    navigate("/register");
  };

  return (
    <AuthenticationShared>
      <Formik
        initialValues={{ userName: "", password: "" }}
        validationSchema={schema}
        onSubmit={(values: LoginRequest) => {
          setLoginError(null); // Xóa lỗi cũ khi người dùng gửi form
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
                    placeholder="Nhập Tài Khoản"
                  />
                  <label style={{ color: "#06BBCC" }} htmlFor="userName">
                    Tài Khoản
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

                {/* Hiển thị thông báo lỗi nếu có */}
                {loginError && (
                  <div className="text-danger text-center mb-3">
                    {loginError}
                  </div>
                )}

                <button
                  style={{
                    backgroundColor: "#06BBCC",
                    borderColor: "#06BBCC",
                  }}
                  type="submit"
                  className="btn btn-primary d-grid w-100"
                >
                  ĐĂNG NHẬP
                </button>

                <p
                  className="text-center"
                  style={{ marginTop: "10px", color: "red" }}
                >
                  <a
                    style={{
                      textDecoration: "underline",
                      color: "#06BBCC",
                      cursor: "pointer",
                    }}
                  >
                    Bạn quên mật khẩu?
                  </a>
                </p>

                <p className="text-center">
                  <span>Chưa có tài khoản ?</span>
                  <a
                    style={{
                      padding: "0.5rem",
                      color: "#06BBCC",
                      cursor: "pointer",
                    }}
                    onClick={handleRegister}
                  >
                    Đăng ký
                  </a>
                </p>

                <button
                  style={{
                    backgroundColor: "#06BBCC",
                    borderColor: "#06BBCC",
                  }}
                  onClick={() => googleLogin()}
                  className="btn btn-primary d-grid w-100"
                >
                  ĐĂNG NHẬP VỚI GOOGLE
                </button>
              </Form>
            </div>
          </div>
        )}
      </Formik>
    </AuthenticationShared>
  );
};

export default Login;
