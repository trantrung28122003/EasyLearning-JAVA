package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.*;
import com.hutech.easylearning.dto.request.*;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.Feedback;
import com.hutech.easylearning.enums.CourseType;
import com.hutech.easylearning.service.*;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private CourseEventService courseEventService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;



    @PostMapping("/addToFeedback")
    ApiResponse<Feedback> createFeedback(@RequestBody FeedbackCreationRequest request) {
        return ApiResponse.<Feedback>builder()
                .result(feedbackService.createFeedback(request))
                .build();
    }

    @PutMapping("/updateFeedback/{feedbackId}")
    ApiResponse<Feedback> updateFeedback(@PathVariable("feedbackId") String feedbackId, @RequestBody FeedbackUpdateRequest request) {
        return ApiResponse.<Feedback>builder()
                .result(feedbackService.updateFeedback(feedbackId, request))
                .build();
    }

    @GetMapping("/getAllFeedback/{courseId}")
    public ApiResponse<FeedbackResponse> getFeedbackForCourse(@PathVariable("courseId") String courseId) {
        return ApiResponse.<FeedbackResponse>builder()
                .result(feedbackService.getFeedbacksByCourse(courseId))
                .build();
    }

    @GetMapping("/purchasedCourses")
    public ApiResponse<List<PurchasedCourseResponse>> purchasedCourses() {
        return ApiResponse.<List<PurchasedCourseResponse>>builder()
                .result(courseService.getCoursePurchasedByUser())
                .build();
    }

    @GetMapping("/searchCourseByName")
    public ApiResponse<List<Course>> searchCourseByName(@RequestParam String courseName) {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.searchCoursesByName(courseName))
                .build();
    }

    @GetMapping("/getCourseById/{courseId}")
    public ApiResponse<Course> getCourseByCourseId(@PathVariable("courseId") String courseId) {
        return ApiResponse.<Course>builder()
                .result(courseService.getCourseById(courseId))
                .build();
    }

    @GetMapping("/schedule/{courseId}")
    public ApiResponse<ScheduleResponse> getScheduleByUser(@PathVariable("courseId") String courseId) {
        return ApiResponse.<ScheduleResponse>builder()
                .result(courseService.getPurchasedCoursesSchedule(courseId))
                .build();
    }

    @GetMapping("/schedule/detailCourseEvent/{courseEventId}")
    public ApiResponse<ScheduleDetailEventResponse> getDetailCourseEvent(@PathVariable("courseEventId") String courseEventId) {
        return ApiResponse.<ScheduleDetailEventResponse>builder()
                .result(courseEventService.getDetailCourseEvent(courseEventId))
                .build();
    }


    @GetMapping("/CourseStatus/{courseId}")
    public ApiResponse<CourseStatusResponse> getCourseStatus(@PathVariable("courseId") String courseId) {
        return ApiResponse.<CourseStatusResponse>builder()
                .result(courseService.getCourseStatus(courseId))
                .build();

    }

    @GetMapping("/notificationByUser")
    public ApiResponse<List<NotificationResponse>> getNotification() {
        return ApiResponse.<List<NotificationResponse>>builder()
                .result(notificationService.getAllNotificationByUser())
                .build();
    }

    @PostMapping("/updateNotificationStatusIsRead")
    public ApiResponse<NotificationResponse> updateNotificationStatus(@RequestParam String notificationId) {
        return ApiResponse.<NotificationResponse>builder()
                .result(notificationService.updateStatusIsRead(notificationId))
                .build();
    }

    @PostMapping("/updateProfile")
    public ApiResponse<UserResponse> updateProfile(
            @RequestParam(value = "fullName") String fullName,  // Tham số fullName
            @RequestParam(value = "file", required = false) MultipartFile file) {
        System.out.println("Full Name from : " + fullName);  // Kiểm tra giá trị fullName
        System.out.println("Request: " + fullName);
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateProfileUser(fullName, file))
                .build();
    }

    @PostMapping("/changePassword")
    public ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try{
            Boolean isSuccess = userService.changePassword(changePasswordRequest);
            if (isSuccess) {
                return ApiResponse.<String>builder()
                        .result("Thay đổi mật khẩu thành công")
                        .build();
            } else {
                return ApiResponse.<String>builder()
                        .code(400)
                        .message("Mật khẩu cũ không đúng")
                        .build();
            }

        }catch (Exception ex) {
            ex.printStackTrace();
            return ApiResponse.<String>builder()
                    .code(500)
                    .message("Đã xảy ra lỗi trong quá trình xác minh mã")
                    .build();
        }
    }

    @PostMapping("/resetPassword")
    public ApiResponse<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try{
           userService.resetPassword(resetPasswordRequest);
           return ApiResponse.<String>builder()
                        .result("Thay đổi mật khẩu thành công")
                        .build();
        }catch (Exception ex) {
            ex.printStackTrace();
            return ApiResponse.<String>builder()
                    .code(500)
                    .message("Đã xảy ra lỗi trong quá trình xác minh mã")
                    .build();
        }
    }
}
