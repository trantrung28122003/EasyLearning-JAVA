import React, { useEffect, useState } from "react";
import { GET_EVENTS_BY_USER } from "../../../constants/API";
import { GetUserEventsResponse } from "../../../model/Course";
import ClientShared from "../Shared/ClientShared";
import { ApplicationResponse } from "../../../model/BaseResponse";
import { DoCallAPIWithToken } from "../../../services/HttpService";
import { useParams } from "react-router-dom";
import "./UserEvents.css";
import { color } from "echarts";
const UserEvents: React.FC = () => {
  const [userCourse, setUserCourse] = useState<GetUserEventsResponse>();
  const { courseId } = useParams();
  const URL = GET_EVENTS_BY_USER + "/" + courseId;
  const doCallGetEventsByUser = () => {
    DoCallAPIWithToken(URL, "get")
      .then((res) => {
        const response: ApplicationResponse<GetUserEventsResponse> = res.data;
        console.log(response.result);
        setUserCourse(response.result);
      })
      .catch(() => {
        setUserCourse(undefined);
      });
  };
  useEffect(() => {
    doCallGetEventsByUser();
  }, []);




  return (
    <ClientShared>
      <div className="event-schedule-area-two bg-color ">
        <div className="container">
          {!userCourse && (
            <div
              className="col-lg-12 text-header"
              style={{ marginTop: "120px", marginBottom: "120px" }}
            >
              <div className="section-title text-center">
                <div className="title-text">
                  <h2 className="h2-text-center">
                    Bạn chưa đăng kí bất kì một khóa học trực tiếp nào cả
                  </h2>
                </div>
              </div>
            </div>
          )}

          <div className="tab-pane" style={{marginTop:"20px"}}>
              <table className="table">
                <thead style={{backgroundColor : "#06BBCC" , fontSize: "20px" , color:"White" }}>
                  <tr>
                    <th scope="col"> Ngày</th>
                    <th scope="col">Giảng viên</th>
                    <th scope="col">Nội dung buổi học</th>
                    <th scope="col">Lớp học</th>
                    <th scope="col"> Thông tin chi tiết </th>
                  </tr>
                </thead>
                <tbody>
                  {userCourse?.courseEventResponse.map((event) => (
                    <tr className="inner-box" style={{ padding: "20px" }}>
                      <th scope="row">
                        <div className="event-date">
                          {/* <span> {event.startTime.substring(0, 10)} </span> */}
                          {(() => {
                              const date = new Date(event.startTime); // Tạo đối tượng Date từ event.startTime
                              
                              const daysOfWeek = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
                              const day = date.getDate(); // Lấy ngày
                              const month = date.getMonth() + 1; // Lấy tháng (getMonth trả về từ 0-11 nên phải +1)
                              const year = date.getFullYear(); // Lấy năm
                              const dayOfWeek = daysOfWeek[date.getDay()]; // Lấy thứ trong tuần
                              
                              return (
                                
                                <div>
                                  <span>
                                    {`${day}/${month}/${year}`} {/* Hiển thị ngày, tháng, năm */}
                                  </span>
                                  <p>{dayOfWeek}</p> {/* Hiển thị thứ */}
                                </div>
                              );
                              
                            })()}
                        </div>
                      </th>
                      <td>
                        <div className="event-img">
                          <img src={userCourse.avatarInstructor} alt="" />
                        </div>
                      </td>
                      <td>
                        <div className="event-wrap">
                          <h3>
                            <a href="#">{event.courseEventName}</a>
                          </h3>
                          <div className="meta" style={{ display: "flex"}}>
                          <div className="organizers" style={{ marginRight: "30px" }}>
                            <a href="#">{userCourse.nameInstructor}</a>
                          </div>
                          <div className="categories">
                          <i className="fas fa-folder"></i> <a  href="#">Tài liệu</a>
                          </div>
          
                        </div>
                        <div className="time">
                            <span>
                              {event.startTime.substring(0, 10)} - {event.endTime.substring(0, 10)}
                            </span>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div className="r-no">
                          <span>{event.location}</span>
                        </div>
                      </td>
                      <td>
                        <div className="primary-btn">
                          <a className="btn btn-primary" href="#">
                            Read More
                          </a>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
      </div>
    </ClientShared>
  );
};

export default UserEvents;
