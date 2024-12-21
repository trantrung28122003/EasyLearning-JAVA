import React from "react";
import AdminShared from "../Shared/AdminShared";

const CreateEvent: React.FC = () => {
  return (
    <AdminShared>
      <h1 className="text-center">Thêm nội dung buổi học cho khóa học</h1>
      <h1 className="text-center" style={{ marginBottom: "40px" }}>
        <strong>@ViewBag.CourseName</strong>{" "}
      </h1>
      <hr />
      <div className="container">
        <form encType="multipart/form-data">
          <div className="text-danger"></div>
          <input
            type="hidden"
            id="CourseId"
            name="CourseId"
            value="@ViewBag.CourseId"
          />

          <div className="form-group">
            <label className="control-label">Nội dung buổi học</label>
            <input className="form-control" id="eventNameInput" />
            <span className="text-danger"></span>
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
            <span className="text-danger"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Ngày bắt đầu</label>
            <input className="form-control" />
            <span className="text-danger"></span>
          </div>
          <div className="form-group">
            <label className="control-label">Ngày kết thúc</label>
            <input className="form-control" />
            <span className="text-danger"></span>
          </div>

          <h1 className="text-center" style={{ marginTop: "20px" }}>
            Thêm nội dung phần học cho buổi học
          </h1>
          <h2 className="text-center" id="lessonHeading"></h2>
          <hr />

          <div className="container">
            <div className="form-group">
              <label className="control-label">Tên phần học</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label">Thời gian bắt đầu</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label">Thời gian kết thúc</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label className="control-label">Mô tả</label>
              <input className="form-control" />
              <span className="text-danger"></span>
            </div>
            <div className="form-group">
              <label>Hình thức:</label>
              <select className="form-control">
                <option value="">Lý thuyết</option>
                <option value="">Bài tập</option>
              </select>
              <span className="text-danger"></span>
            </div>
            {/* @if (ViewBag.IsOnlineCourse)
            {
                <div className="form-group">
                    <label className="control-label">Chọn nguồn video:</label><br>
                    <input type="radio" name="videoSource" id="onlineSource" checked style="margin-left: 24px"> <label for="onlineSource">Từ đường dẫn trực tuyến</label><br>
                    <input type="radio" name="videoSource" id="uploadSource" style="margin-left: 24px"> <label for="uploadSource">Từ máy tính</label>
                </div>

                <div id="onlineVideo" className="form-group">
                    <label asp-for="TrainingPartViewModel.VideoUrl" className="control-label">Đường dẫn video</label>
                    <input asp-for="TrainingPartViewModel.VideoUrl" className="form-control" />
                    <span asp-validation-for="TrainingPartViewModel.VideoUrl" className="text-danger"></span>
                </div>
                <div className="form-group" id="uploadVideo" style="display: none;">
                    <label asp-for="TrainingPartViewModel.Video" className="control-label">Tải lên video từ máy tính:</label>
                    <input asp-for="TrainingPartViewModel.Video" className="form-control" type="file" />
                    <span asp-validation-for="TrainingPartViewModel.VideoUrl" className="text-danger"></span>
                </div>

                <div className="form-group">
                    <input type="checkbox" asp-for="TrainingPartViewModel.IsFree" id="IsFree" />
                    <label asp-for="TrainingPartViewModel.IsFree" className="control-label">Miễn phí phần bài học này</label>
                    <span asp-validation-for="TrainingPartViewModel.IsFree" className="text-danger"></span>
                </div>
            } */}
          </div>
          <div className="form-group text-center">
            <input
              type="submit"
              value="Thêm một buổi học"
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

export default CreateEvent;
