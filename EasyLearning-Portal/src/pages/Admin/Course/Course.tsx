import React from "react";
import AdminShared from "../Shared/AdminShared";

const Course: React.FC = () => {
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
          {
            <tr>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td>
                <button className="btn btn-info">Sửa</button>
                <button className="btn btn-warning">Chi tiết</button>
                <button className="btn btn-danger">Xóa</button>
                <button className="btn btn-info">Danh sách phần học</button>
                <button className="btn btn-info">Buổi học</button>
              </td>
            </tr>
          }
        </tbody>
      </table>
    </AdminShared>
  );
};

export default Course;
