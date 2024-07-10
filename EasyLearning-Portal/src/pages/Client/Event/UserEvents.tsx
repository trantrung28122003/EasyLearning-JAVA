import React from "react";
import ClientShared from "../Shared/ClientShared";

const UserEvents: React.FC = () => {
  return (
    <ClientShared>
      <div className="event-schedule-area-two bg-color ">
        <div className="container">
          {/* <div className="row">
            @foreach (var itemOrderDetail in Model.Orders)
            {
                if (itemOrderDetail.UserId == Model.currentUserId)
                {
                    <div className="col-lg-12 text-header">
                        <div className="section-title text-center">
                            <div className="title-text">
                                <h2 className="h2-text-center">Thời khóa biểu cho các khóa học trực tiếp của bạn</h2>
                            </div>
                        </div>
                    </div>
                    break;
                }
                else
                {
                    <div className="col-lg-12 text-header" style="margin-top: 120px; margin-bottom: 120px;">
                        <div className="section-title text-center">
                            <div className="title-text">
                                <h2 className="h2-text-center">Bạn chưa đăng kí bất kì một khóa học trực tiếp nào cả</h2>
                            </div>
                        </div>
                    </div>
                    break;
                }
            }
            <!-- /.col end-->
        </div>

        <div className="row">
            <div className="col-lg-12">
                @{
                    bool isFirstCourse = true;
                }
                <ul className="nav custom-tab" id="myTab" role="tablist">
                    @foreach (var itemCourse in Model.Courses)
                    {
                        <li className="nav-item">
                            <a className="nav-link @(isFirstCourse ? "active" : "")" id="@("tab-" + itemCourse.Id)" data-toggle="tab" href="#@(itemCourse.Id)" role="tab" aria-controls="@itemCourse.Id" aria-selected="@(isFirstCourse ? "true" : "false")" style="color: #2bc5d4">@itemCourse.CoursesName</a>
                        </li>
                        isFirstCourse = false;
                    }

                </ul>

                @{
                    bool isFirstCourse1 = true;
                }
                <div className="tab-content" id="myTabContent">
                    @foreach (var itemCourse in Model.Courses)
                    {

                        <div className="tab-pane fade @(isFirstCourse1 ? "show active" : "")" id="@itemCourse.Id" role="tabpanel" aria-labelledby="@("tab-" + itemCourse.Id)">
                            <div className="table-responsive">
                                <table className="table">
                                    <thead>
                                        <tr>
                                            <th className="text-center" scope="col">Ngày</th>
                                            <th scope="col">Giảng viên</th>
                                            <th scope="col">Nội dung buổi học</th>
                                            <th scope="col">Lớp học</th>
                                            <th className="text-center" scope="col">Thông tin chi tiết</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        @{
                                            var listCourseEventsId = Model.TrainingParts
                                            .Where(tp => tp.CoursesId == itemCourse.Id) 
                                            .Select(tp => tp.EventId) 
                                            .Distinct() 
                                            .ToList();
                                        }

                                        @foreach (var itemCourseEventId in listCourseEventsId)
                                        {
                                            @foreach (var itemCourseEvent in Model.CourseEvents)
                                            {
                                                if (itemCourseEvent.Id == itemCourseEventId)
                                                {
                                                    <tr className="inner-box">
                                                        <th scope="row">
                                                            <div className="event-date">
                                                                <span> @itemCourseEvent.DateStart.Day </span>
                                                                @{
                                                                    @using System.Globalization

                                                                    string monthName = CultureInfo.CurrentCulture.DateTimeFormat.GetMonthName(itemCourseEvent.DateStart.Month);
                                                                }
                                                                <p>@monthName</p>
                                                            </div>
                                                        </th>
                                                        <td>
                                                            <div className="event-img">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" />
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div className="event-wrap">
                                                                <h3><a href="#">@itemCourseEvent.EventName</a></h3>
                                                                <div className="meta">
                                                                    <div className="organizers">
                                                                        <a href="#">@itemCourse.Instructor</a>
                                                                    </div>
                                                                    <div className="categories">
                                                                        <a href="#">Tài liệu</a>
                                                                    </div>
                                                                    <div className="time">
                                                                        @{
                                                                            
                                                                            DateTime startTime = itemCourseEvent.DateStart;
                                                                            DateTime endTime = itemCourseEvent.DateEnd;

                                                                           
                                                                            string startTimeFormatted = startTime.ToString("hh:mm tt", CultureInfo.InvariantCulture);
                                                                            string endTimeFormatted = endTime.ToString("hh:mm tt", CultureInfo.InvariantCulture);

                                                                          
                                                                            TimeSpan duration = endTime - startTime;
                                                                            string durationFormatted = duration.ToString(@"h\h\ m\m", CultureInfo.InvariantCulture);
                                                                        }
                                                                        <span>@startTimeFormatted - @endTimeFormatted @durationFormatted</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div className="r-no">
                                                                <span>@itemCourseEvent.Location</span>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div className="primary-btn">
                                                                <a className="btn btn-primary" href="#" onclick="showEventDetail('@itemCourseEvent.Id')">Read More</a>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                }

                                            }
                                        }
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        isFirstCourse1 = false;
                    }
                </div>
            </div>
        </div>
        @foreach (var itemCourse in Model.Courses)
        {
            var listCourseEventsId = Model.TrainingParts
                .Where(tp => tp.CoursesId == itemCourse.Id) 
                .Select(tp => tp.EventId) 
                .Distinct() 
                .ToList();
            @foreach (var itemCourseEventId in listCourseEventsId)
            {
                @foreach(var itemCourseEvent in Model.CourseEvents)
                {
                    if (itemCourseEvent.Id == itemCourseEventId)
                    {
                        <div className="col-lg-8 col-xl-6 event-details" id="event-detail-@itemCourseEvent.Id" style="display: none;">
                            <div className="card border-top border-bottom border-3" style="border-color: #06BBCC !important;">
                                <div className="card-body p-5">
                                    <button className="btn-close" onclick="hideEventDetail('@itemCourseEvent.Id')"
                                    style="position: absolute; top: 12px; right: 12px; font-size: 20px;"></button>
                                    <p className="lead fw-bold mb-5" style="color: #06BBCC; text-align:center; font-size:28px">Chi tiết buổi học</p>
                                    <div className="row">
                                        <div className="col mb-3">
                                            <p className="small text-muted mb-1" style="color: #06BBCC">Thời gian</p>
                                            <p> ngày @itemCourseEvent.DateStart.Day tháng @itemCourseEvent.DateStart.Month</p>
                                        </div>
                                        <div className="col mb-3">
                                            <p className="small text-muted mb-1"  style="color: #06BBCC">Giảng viên</p>
                                            <p>@itemCourse.Instructor</p>
                                        </div>
                                    </div>
                                    <p className="lead fw-bold pb-2 mt-3" style="color: #06BBCC;">Nội dung phần học</p>
                                    <div className="mx-n5 px-5 py-4" style="background-color: #f2f2f2;overflow-y: auto; max-height: 150px;">
                                        @foreach (var itemTrainingPart in Model.TrainingParts)
                                        {
                                            if (itemCourseEvent.Id == itemTrainingPart.EventId)
                                            {
                                                <div className="row">
                                                    <div className="col-md-8 col-lg-9">
                                                        <p>@itemTrainingPart.TrainingPartName</p>
                                                    </div>
                                                    <div className="col-md-4 col-lg-3">
                                                        <p>@(itemTrainingPart.TrainingPartType == TrainingPartType.Lesson ? "Lý thuyết" : "Bài tập") </p>
                                                    </div>
                                                </div>
                                                 <div className="row">
                                                    <div className="col-md-8 col-lg-9">
                                                        <p>@itemTrainingPart.TrainingPartName</p>
                                                    </div>
                                                    <div className="col-md-4 col-lg-3">
                                                        <p>@(itemTrainingPart.TrainingPartType == TrainingPartType.Lesson ? "Lý thuyết" : "Bài tập") </p>
                                                    </div>
                                                </div>

                                            }
                                        }
                                    </div>
                                    <p className="lead fw-bold pb-2 mt-3" style="color: #06BBCC;">Chu trình buổi học</p>
                                    <div className="row">
                                        <div className="col-lg-12">

                                            <div className="horizontal-timeline">

                                                <ul className="list-inline items d-flex justify-content-between">
                                                    <li className="list-inline-item items-list">
                                                        <p className="py-1 px-2 text-white" style="background-color: #06BBCC; border-radius: 12px">Bắt đầu và giới thiệu</p>
                                     
                                                    </li>
                                                    <li className="list-inline-item items-list">
                                                        <p className="py-1 px-2 text-white" style="background-color: #06BBCC; border-radius: 12px">Lý thuyết</p>
                                                    
                                                    </li>
                                                    @{
                                                        bool hasExercise = Model.TrainingParts.Any(x => x.TrainingPartType == TrainingPartType.Exercise && x.EventId == itemCourseEvent.Id);
                                                    }
                                                    <li className="list-inline-item items-list">
                                                        <p className="py-1 px-2  text-white" style="background-color: #06BBCC; border-radius: 12px">@(hasExercise ? "Bài tập và thỏa luận" : "Lý thuyết")
                                                        </p>
                                                    </li>
                                                    <li className="list-inline-item items-list text-end" style="margin-right: 8px;">
                                                        <p className="py-1 px-2  text-white" style="background-color: #06BBCC; border-radius: 12px; margin-right: -8px;">Kết thúc</p>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <p className="pt-2 mb-0">Bạn muốn có tài liệu? <a href="#!" style="color: #06BBCC;">Hãy ấn ở đây để tải</a></p>
                                </div>
                            </div>
                        </div>
                    }
                }
            }
        }*/}
        </div>
      </div>
    </ClientShared>
  );
};

export default UserEvents;
