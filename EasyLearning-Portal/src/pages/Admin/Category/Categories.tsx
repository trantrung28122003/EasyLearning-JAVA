import React from "react";
import AdminShared from "../Shared/AdminShared";

const Categories: React.FC = () => {
  return (
    <AdminShared>
      <h1 style={{ textAlign: "center" }}>Danh sách thể loại</h1>
      <hr />
      <p>
        <a asp-action="Create" className="btn btn-info">
          {" "}
          <i className="fa fa-plus" style={{ marginRight: "10px" }}></i> Thêm
          thể loại mới
        </a>
      </p>

      <table className="table table-striped table-bordered">
        <thead style={{ backgroundColor: "#06bbcc", color: "white" }}>
          <tr>
            <th>Tên thể loại</th>
            <th>Ngày tạo</th>
            <th>Ngày cập nhập gần nhất</th>
            <th>Chức năng</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td></td>
            <td></td>
            <td></td>

            <td>
              <button className="btn btn-info">Sửa</button>
              <button className="btn btn-warning">Chi tiết</button>
              <button className="btn btn-danger">Xóa</button>
            </td>
          </tr>
        </tbody>
      </table>
    </AdminShared>
  );
};

export default Categories;
