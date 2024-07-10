import React from "react";
import AdminShared from "../Shared/AdminShared";

const UpdateEvent: React.FC = () => {
  return (
    <AdminShared>
      <h1 className="text-center">Cập nhập thông tin buổi học</h1>
      <h1 className="text-center" style={{ marginBottom: "40px" }}>
        <strong>1</strong>{" "}
      </h1>
      <hr />
      <div className="container">
        <form encType="multipart/form-data">
          <div className="text-danger"></div>
          <input
            type="hidden"
            id="OriginalCourseId"
            name="OriginalCourseId"
            value="@ViewBag.OriginalCourseId"
          />
          <div className="form-group">
            <label className="control-label">Chọn khóa học</label>
            <select className="form-control"></select>
            <span className="text-danger"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Nội dung buổi học</label>
            <input className="form-control" id="eventNameInput" />
            <span asp-validation-for="EventName" className="text-danger"></span>
          </div>
          <div className="form-group">
            <label>Hình thức:</label>
            <select className="form-control" id="EventType">
              <option value="">Trực tuyến</option>
              <option value="">Trực tiếp</option>
            </select>
            <span className="text-danger"></span>
          </div>

          <div className="form-group" id="onlineRoom">
            <label className="control-label">Link phòng học online</label>
            <input type="text" className="form-control" />
            <span className="text-danger"></span>
          </div>

          <div className="form-group" id="offlineLocation">
            <label className="control-label">Địa chỉ</label>
            <input className="form-control" />
            <span asp-validation-for="Location" className="text-danger"></span>
          </div>
          <div className="form-group">
            <label asp-for="DateStart" className="control-label">
              Ngày bắt đầu
            </label>
            <input asp-for="DateStart" className="form-control" />
            <span asp-validation-for="DateStart" className="text-danger"></span>
          </div>
          <div className="form-group">
            <label asp-for="DateEnd" className="control-label">
              Ngày kết thúc
            </label>
            <input asp-for="DateEnd" className="form-control" />
            <span asp-validation-for="DateEnd" className="text-danger"></span>
          </div>

          <h1 className="text-center">Danh sách nội dung của buổi học </h1>
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
              {/* @if (Model.TrainingParts != null)
                {
                    foreach (var item in Model.TrainingParts)
                    {
                        <tr>
                            <td>@item.TrainingPartName</td>
                            <td>@item.StartTime</td>
                            <td>@item.EndTime</td>
                            @if (@item.TrainingPartType == TrainingPartType.Lesson)
                            {
                                <td>Lý thuyết</td>
                            }
                            else if (@item.TrainingPartType == TrainingPartType.Exercise)
                            {
                                <td>Bài tập</td>
                            }
                            else
                            {
                                <td>Không xác định</td>
                            }

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
          <input type="hidden" asp-for="TrainingParts" name="" />
          <div className="form-group text-center">
            <input
              type="submit"
              value="Lưu thay đổi"
              className="btn btn-outline-info btn-lg btn-block"
            />
          </div>
        </form>
      </div>
      <div>
        <a className="btn btn-outline-danger">Quay lại</a>
      </div>
    </AdminShared>
  );
};

export default UpdateEvent;
