import React from "react";
import AdminShared from "../Shared/AdminShared";

const Events: React.FC = () => {
  return (
    <AdminShared>
      <div>
        <h1 className="text-center">Nội dung các buổi học của khóa học</h1>
        <h1 className="text-center" style={{ marginBottom: "40px" }}>
          <strong></strong>{" "}
        </h1>
        <hr />
        <p>
          <a className="btn btn-outline-info">
            {" "}
            <i className="fa fa-plus" style={{ marginRight: "10px" }}></i>Tạo
            buổi học mới
          </a>
          <a className="btn btn-outline-danger">Quay lại</a>
        </p>
        <table className="table table-striped table-bordered">
          <thead style={{ backgroundColor: "#06bbcc", color: "white" }}>
            <tr>
              <th>Nội dung buổi học</th>
              <th>Thời gian bắt đầu</th>
              <th>Thời gian kết thúc</th>
              <th>Địa chỉ</th>
              <th>Hình thức</th>
              <th>Chức năng</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td>Trực tuyến</td>
              {/* <td>Trực tiếp</td>
            <td>Không xác định</td> */}

              <td>
                <button className="btn btn-info">Sửa</button>
                <button className="btn btn-warning">Chi tiết</button>
                <button className="btn btn-danger">Xóa</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </AdminShared>
  );
};

export default Events;
