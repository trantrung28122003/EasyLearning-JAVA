import React from "react";
import AdminShared from "../Shared/AdminShared";

const EventDetail: React.FC = () => {
  return (
    <AdminShared>
      <h1 className="text-center">Chi tiết buổi học</h1>
      <h1 className="text-center" style={{ marginBottom: "40px" }}>
        <strong>EventName</strong>{" "}
      </h1>
      <hr />
      <div className="container">
        <div>
          <div className="form-group">
            <label className="control-label">Khóa học</label>
            <span className="form-control"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Nội dung buổi học</label>
            <span className="form-control">EventName</span>
          </div>
          <div className="form-group">
            <label className="control-label">Hình thức</label>
            <span className="form-control"></span>
          </div>

          <div className="form-group" id="onlineRoom">
            <label className="control-label">Link phòng học online</label>
            <span className="form-control">OnlineRoomUrl</span>
          </div>

          <div className="form-group" id="offlineLocation">
            <label className="control-label">Địa chỉ</label>
            <span className="form-control">Location</span>
          </div>

          <div className="form-group">
            <label className="control-label">Ngày bắt đầu</label>
            <span className="form-control">DateStart</span>
          </div>
          <div className="form-group">
            <label className="control-label">Ngày kết thúc</label>
            <span className="form-control">DateEnd</span>
          </div>

          <h1 className="text-center">Danh sách nội dung của buổi học</h1>
          <hr />
          <table className="table table-striped table-bordered">
            <thead style={{ backgroundColor: "#06bbcc", color: "white" }}>
              <tr>
                <th>Tên nội dung</th>
                <th>Ngày bắt đầu</th>
                <th>Ngày kết thúc</th>
                <th>Hình thức</th>
              </tr>
            </thead>
            <tbody>
              {/* @if (Model.TrainingParts != null && Model.TrainingParts.Any())
                {
                    foreach (var item in Model.TrainingParts)
                    {
                        <tr>
                            <td>@item.TrainingPartName</td>
                            <td>@item.StartTime</td>
                            <td>@item.EndTime</td>
                            <td>@(item.TrainingPartType == TrainingPartType.Lesson ? "Lý thuyết" : (item.TrainingPartType == TrainingPartType.Exercise ? "Bài tập" : "Không xác định"))</td>
                        </tr>
                    }
                }
                else
                {
                    <tr>
                        <td colspan="4">Không có nội dung cho buổi học này</td>
                    </tr>
                } */}
            </tbody>
          </table>
        </div>
        <div className="form-group text-center">
          <a
            asp-controller="Event"
            asp-action="Index"
            asp-route-courseId="CourseId"
            className="btn btn-outline-info btn-lg btn-block"
          >
            Quay lại
          </a>
        </div>
      </div>
    </AdminShared>
  );
};

export default EventDetail;
