import React, { useEffect, useState } from "react";

import ClientShared from "../Shared/ClientShared";

import "./UserSchedule.css";
import { CourseSlim } from "../../../model/Course";
import { GET_COURSE_BY_USER } from "../../../constants/API";
import { DoCallAPIWithToken } from "../../../services/HttpService";
import { ApplicationResponse } from "../../../model/BaseResponse";
import dayjs from "dayjs";
import "dayjs/locale/vi";

type EventSchedule = {
  eventId: string;
  courseName: string;
  eventDate: string;
  startTime: string;
  endTime: string;
  location: string;
  instructor: string;
};

type Schedule = {
  date: string;
  events: EventSchedule[];
};

const UserEvents: React.FC = () => {
  const [userCourse, getUserCourse] = useState<CourseSlim[]>([]);
  const doCallGetCourseByUser = () => {
    DoCallAPIWithToken(GET_COURSE_BY_USER, "get").then((res) => {
      const response: ApplicationResponse<CourseSlim[]> = res.data;
      console.log(response.result);
      getUserCourse(response.result);
    });
  };

  useEffect(() => {
    doCallGetCourseByUser();
  }, []);

  const [currentWeekStart, setCurrentWeekStart] = useState(
    dayjs().startOf("week").add(1, "day")
  );

  const [scheduleData, setScheduleData] = useState<Schedule[]>([]);

  const getScheduleForWeek = (startOfWeek: dayjs.Dayjs) => {
    const scheduleForWeek: Schedule[] = [];
    let hasSchedule = false;

    for (let i = 0; i < 7; i++) {
      const currentDate = startOfWeek.add(i, "day").format("YYYY-MM-DD");
      const eventsForDate: EventSchedule[] = [];

      userCourse.forEach((course) => {
        course.courseEventResponses.forEach((event) => {
          if (dayjs(event.startTime).format("YYYY-MM-DD") === currentDate) {
            eventsForDate.push({
              eventId: event.id,
              courseName: course.courseName,
              eventDate: currentDate,
              location: event.location,
              instructor: course.instructor,
              startTime: event.startTime,
              endTime: event.endTime,
            });
          }
        });
      });

      if (eventsForDate.length > 0) {
        scheduleForWeek.push({ date: currentDate, events: eventsForDate });
        hasSchedule = true;
      }
    }

    if (hasSchedule) {
      setScheduleData(scheduleForWeek);
    } else {
      setScheduleData([]);
    }
  };

  const goToPreviousWeek = () => {
    setCurrentWeekStart((prev) => prev.subtract(1, "week"));
  };

  const goToNextWeek = () => {
    setCurrentWeekStart((prev) => prev.add(1, "week"));
  };

  const formatDateWithWeekDay = (date: string) => {
    const formattedDate = dayjs(date)
      .locale("vi")
      .format("dddd, [ngày] DD/MM/YYYY");
    return formattedDate.charAt(0).toUpperCase() + formattedDate.slice(1);
  };

  useEffect(() => {
    getScheduleForWeek(currentWeekStart);
    console.log("1231232", userCourse);
  }, [currentWeekStart]);

  return (
    <ClientShared>
      <div className="page-content">
        <div className="container px-0">
          <div className="row">
            <div className="col-12 col-lg-12" style={{ marginTop: "28px" }}>
              <div className="row" style={{ marginBottom: "50px" }}>
                <div className="col-12">
                  <div className=" title-text text-center text-150">
                    <span>
                      <strong style={{ fontSize: "30px", color: "#06BBCC" }}>
                        ---------------------------------------- THỜI KHÓA BIỂU
                        ----------------------------------------
                      </strong>
                    </span>
                  </div>
                </div>
              </div>

              <div className="row d-flex justify-content-between align-items-center">
                <div className="col-sm-4 d-flex justify-content-start">
                  <div className="text-grey-m2">
                    <button
                      className="custom-btn btn btn-primary"
                      onClick={goToPreviousWeek}
                    >
                      <i
                        className="fa fa-arrow-left mr-1"
                        style={{ marginRight: "12px" }}
                      ></i>
                      Tuần trước
                    </button>
                  </div>
                </div>
                <div className="col-sm-4 d-flex justify-content-center">
                  <div className="text-grey-m2">
                    <span className="text-150 text-secondary-m1">
                      Từ ngày{" "}
                      <strong style={{ color: "black" }}>
                        {currentWeekStart.format("DD/MM/YYYY")}
                      </strong>{" "}
                      đến{" "}
                      <strong style={{ color: "black" }}>
                        {currentWeekStart.add(6, "days").format("DD/MM/YYYY")}
                      </strong>
                    </span>
                  </div>
                </div>
                <div className="col-sm-4 d-flex justify-content-end">
                  <div className="text-grey-m2">
                    <button
                      className="custom-btn btn btn-primary"
                      onClick={goToNextWeek}
                    >
                      Tuần Sau
                      <i
                        className="fa fa-arrow-right mr-1"
                        style={{ marginLeft: "12px" }}
                      ></i>{" "}
                    </button>
                  </div>
                </div>
              </div>
            </div>

            {scheduleData.length > 0 ? (
              <>
                <div className="mt-4">
                  <div className="row mb-2 mb-sm-0 py-25 bgc-default-l4">
                    <div className="d-none d-sm-block col-2 header-text">
                      Giờ học
                    </div>
                    <div className="col-6 col-sm-4 header-text">
                      Tên khóa học
                    </div>
                    <div className="d-none d-sm-block col-3 header-text">
                      Tên giảng viên
                    </div>
                    <div className="d-none d-sm-block col-3 text-95 header-text">
                      Thông tin tiết học
                    </div>
                  </div>

                  {scheduleData.map((schedule, i) => (
                    <div key={i}>
                      <div
                        className="row text-600 text-white bgc-default py-25"
                        style={{ backgroundColor: "#06BBCC" }}
                      >
                        <div className="d-none d-sm-block text-center">
                          <strong style={{ fontSize: "18px " }}>
                            {" "}
                            {formatDateWithWeekDay(schedule.date)}{" "}
                          </strong>
                        </div>
                      </div>
                      {schedule.events.map((eventCourses, index) => (
                        <div
                          className={`row mb-2 mb-sm-0 py-25 bgc-default-l4 ${
                            index < schedule.events.length - 1
                              ? "border-bottom"
                              : ""
                          }`}
                          key={eventCourses.eventId}
                        >
                          <div className="d-none d-sm-block col-2">
                            {dayjs(eventCourses.startTime).format("hh:mm A")} -{" "}
                            {dayjs(eventCourses.endTime).format("hh:mm A")}
                          </div>
                          <div className="col-6 col-sm-4">
                            {eventCourses.courseName}
                          </div>
                          <div className="d-none d-sm-block col-3">
                            {eventCourses.instructor}
                          </div>
                          <div className="d-none d-sm-block col-3 text-95">
                            {eventCourses.location}
                          </div>
                        </div>
                      ))}
                    </div>
                  ))}
                </div>
              </>
            ) : (
              <div className="centered-content">
                <img
                  src="https://api.hutech.edu.vn/file-publish/sinh-vien-edu-vn/nodata_tkb.gif"
                  style={{ width: "200px" }}
                  alt="No data"
                />
                <h2 className="warning-message">
                  Bạn không có buổi học nào trong tuần này!
                </h2>
                <h1>Vui lòng chọn tuần khác để xem thời khóa biểu</h1>
                <p>
                  Theo dõi website thường xuyên để cập nhật lịch học mới nhất,
                  giúp bạn không bỏ lỡ bất kỳ buổi học nào!
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    </ClientShared>
  );
};

export default UserEvents;
