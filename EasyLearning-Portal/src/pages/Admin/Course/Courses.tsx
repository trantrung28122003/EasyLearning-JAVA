import React, { useEffect, useState } from "react";
import AdminShared from "../Shared/AdminShared";
import { DoCallAPIWithToken } from "../../../services/HttpService";
import { BASE_URL_COURSE } from "../../../constants/API";
import { HTTP_OK } from "../../../constants/HTTPCode";
import { Course } from "../../../model/Course";

const Courses: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);

  const fectchCourses = () => {
    DoCallAPIWithToken(BASE_URL_COURSE, "GET")
      .then((res) => {
        if (res.status === HTTP_OK) {
          setCourses(res.data);
        }
      })
      .catch((err) => {});
  };

  useEffect(() => {
    fectchCourses();
  }, []); 

  return (
    <AdminShared>
      <table className="table table-striped table-bordered">
        <thead style={{ backgroundColor: "#06bbcc", color: "white" }}>
          <tr>
            <th scope="col">Tên</th>
            <th scope="col">Giá</th>
            <th scope="col">Ngày bắt đầu</th>
            <th scope="col">Ngày kết thúc</th>
            <th scope="col">Ngày hạn đăng ký</th>
            <th scope="col" style={{ width: "80px" }}>
              Sỉ số
            </th>
            <th scope="col">Chức năng</th>
          </tr>
        </thead>
        <tbody>
          {courses &&
            courses.map((course, index) => (
              <tr key={index}>
                <td>{course.courseName}</td>
                <td>{course.coursePrice}</td>
                <td>{course.startDate}</td>
                <td>{course.endDate}</td>
                <td>{course.registrationDeadline}</td>
                <td>{course.changedBy}</td>
                <td>
                  <button className="btn btn-info">Sửa</button>
                  <button className="btn btn-warning">Chi tiết</button>
                  <button className="btn btn-danger">Xóa</button>
                  <button className="btn btn-info">Danh sách phần học</button>
                  <button className="btn btn-info">Buổi học</button>
                </td>
              </tr>
            ))}
        </tbody>
      </table>
    </AdminShared>
  );
};

export default Courses;
