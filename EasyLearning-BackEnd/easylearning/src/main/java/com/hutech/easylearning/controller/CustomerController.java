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
    @Autowired
    private UserNoteService userNoteService;
    @Autowired
    private UserFavoriteService userFavoriteService;
    @Autowired
    private OrderService orderService;

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

    @GetMapping("/CourseStatus")
    public ApiResponse<CourseStatusResponse> getCourseStatus(@PathParam("courseId") String courseId) {
        return ApiResponse.<CourseStatusResponse>builder()
                .result(courseService.getCourseStatus(courseId))
                .build();

    }

    @PostMapping("/toggleFavorite")
    public ApiResponse<Boolean> toggleFavorite(@PathParam("courseId") String courseId) {
        return ApiResponse.<Boolean>builder()
                .result(userFavoriteService.toggleFavorite(courseId))
                .build();

    }

    @GetMapping("/favoritesCourseByUser")
    public ApiResponse<List<Course>> favoritesByUser() {
        return ApiResponse.<List<Course>>builder()
                .result(userFavoriteService.getFavoriteCoursesByUserId())
                .build();
    }

    @GetMapping("/purchaseHistory")
    public ApiResponse<PurchaseHistoryResponse> purchasesHistory() {
        return ApiResponse.<PurchaseHistoryResponse>builder()
                .result(orderService.getPurchaseHistory())
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

    @GetMapping("/getNotesByCourseAndUser")
    public ApiResponse<List<UserNoteResponse>> getNotesByCourseAndUser(@RequestParam String courseId) {
        try{

            return ApiResponse.<List<UserNoteResponse>>builder()
                    .result(userNoteService.getNotesByCourseIdAndUserId(courseId))
                    .build();
        }catch (Exception ex) {
            ex.printStackTrace();
            return ApiResponse.<List<UserNoteResponse>>builder()
                    .code(500)
                    .message("Đã xảy ra lỗi trong quá trình lấy dữ liệu")
                    .build();
        }
    }

    @PostMapping("/addNotesByCourseAndUser")
    public ApiResponse<UserNoteResponse> addNotesByCourseAndUser(@RequestBody UserNoteCreationRequest request) {
        try{
            return ApiResponse.<UserNoteResponse>builder()
                    .result(userNoteService.addUserNote(request))
                    .build();
        }catch (Exception ex) {
            ex.printStackTrace();
            return ApiResponse.<UserNoteResponse>builder()
                    .code(500)
                    .message("Đã xảy ra lỗi trong quá trình sửa dữ liệu")
                    .build();
        }
    }

    @PostMapping("/updateNotesByCourseAndUser")
    public ApiResponse<UserNoteResponse> getNotesByCourseAndUser(@RequestBody UserNoteUpdateRequest request) {
        try{
            return ApiResponse.<UserNoteResponse>builder()
                    .result(userNoteService.updateUserNote(request))
                    .build();
        }catch (Exception ex) {
            ex.printStackTrace();
            return ApiResponse.<UserNoteResponse>builder()
                    .code(500)
                    .message("Đã xảy ra lỗi trong quá trình sửa dữ liệu")
                    .build();
        }
    }

    @DeleteMapping("/deleteNotesByCourseAndUser")
    public ApiResponse<Void> deleteNotesByCourseAndUser(@RequestParam String userNoteId) {
        try {
            userNoteService.deleteNoteById(userNoteId);
            return ApiResponse.<Void>builder()
                    .result(null)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ApiResponse.<Void>builder()
                    .code(500)
                    .message("Đã xảy ra lỗi trong quá trình xóa ghi chú")
                    .build();
        }
    }

    @GetMapping("/addCourseFree")
    public ApiResponse<String> addCourseFree(@RequestParam String courseId) {
        try{

            if (orderService.addFreeCourseOrder(courseId)) {
                return ApiResponse.<String>builder()
                        .code(200)
                        .result("Thêm thành công khóa học miễn phí")
                        .build();
            } else {
                return ApiResponse.<String>builder()
                        .code(400)
                        .message("Không thành công")
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

}
