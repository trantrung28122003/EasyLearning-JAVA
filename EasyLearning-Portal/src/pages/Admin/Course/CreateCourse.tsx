import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as yup from "yup";
import {
  APIRegisterRequest,
  RegisterRequest,
} from "../../../model/Authentication";
import { ErrorMessage, Field, Form, Formik } from "formik";
import {
  DoCallAPIWithOutToken,
  DoCallAPIWithToken,
} from "../../../services/HttpService";
import { BASE_URL_CREATE_COURSE, REGISTER_URL } from "../../../constants/API";
import { HTTP_OK } from "../../../constants/HTTPCode";
import DataLoader from "../../../components/lazyLoadComponent/DataLoader";
import AuthenticationShared from "../../Client/Authentication/Shared/AuthenticationShared";
import { CourseRequest } from "../../../model/Course";

const Register: React.FC = () => {
  const navigate = useNavigate();
  const schema = yup.object().shape({
    courseName: yup
      .string()
      .min(8, "Tên tài khoản phải có ít nhất 8 ký tự")
      .max(24, "Tên tài khoản không được vượt quá 24 ký tự")
      .required("Tên tài khoản là bắt buộc"),
  });

  const doRegister = (request: CourseRequest) => {
    const formData = new FormData();
    formData.append("courseName", request.courseName);

    DoCallAPIWithToken(BASE_URL_CREATE_COURSE, "post", formData)
      .then((res) => {
        if (res.status === HTTP_OK) {
          navigate("/login");
        }
      })
      .catch((err) => {});
  };

  return (
    <AuthenticationShared>
      <Formik
        initialValues={{
          courseName: "",
        }}
        validationSchema={schema}
        onSubmit={(values: CourseRequest) => {
          const requestPayload: CourseRequest = {
            courseName: values.courseName,
          };
          doRegister(requestPayload);
        }}
        validateOnChange
      >
        {({}) => (
          <div>
            <h1 className="text-center ">Thêm khóa học mới</h1>
            <hr />
            <div className="container">
              <Form>
                <div className="text-danger"></div>
                <div className="form-group">
                  <label className="control-label">Tên khóa học</label>
                  <Field
                    className="form-control"
                    name="courseName"
                    type="text"
                  />
                  <ErrorMessage
                    name="courseName"
                    component="div"
                    className="text-danger"
                  />
                </div>
                {/* <div className="form-group">
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
                </div> */}

                <div className="form-group text-center">
                  <input
                    type="submit"
                    value="Thêm khóa học"
                    className="btn btn-outline-info btn-lg btn-block"
                  />
                </div>
              </Form>
            </div>
            <div>
              <a className="btn btn-outline-danger">Quay lại</a>
            </div>
          </div>
        )}
      </Formik>
      <p className="text-center" style={{ marginTop: "8px" }}>
        <span>Đã Có Tài Khoản ? </span>

        <span style={{ color: "#06BBCC", cursor: "pointer" }}> Đăng Nhập</span>
      </p>
    </AuthenticationShared>
  );
};

export default Register;
