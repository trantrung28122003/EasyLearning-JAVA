package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.FeedbackResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.FeedbackCreationRequest;
import com.hutech.easylearning.dto.request.FeedbackUpdateRequest;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.Feedback;
import com.hutech.easylearning.enums.CourseType;
import com.hutech.easylearning.service.CourseService;
import com.hutech.easylearning.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private FeedbackService feedbackService;

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
                .result(feedbackService.getFeedbackByCourse(courseId))
                .build();
    }

    @GetMapping("/purchasedCourses")
    public ApiResponse<List<Course>> purchasedCourses() {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.getCoursePurchasedByUser())
                .build();
    }

    @GetMapping("/purchasedOfflineCourses")
    public ApiResponse<List<Course>> purchasedOfflineCourses() {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.getCoursePurchasedByUser().stream()
                        .filter(course -> course.getCourseType() == CourseType.OFFLINE)
                        .collect(Collectors.toList()))
                .build();
    }

    @GetMapping("/purchasedOnlineCourses")
    public ApiResponse<List<Course>> purchasedOnlineCourses() {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.getCoursePurchasedByUser().stream()
                        .filter(course -> course.getCourseType() == CourseType.ONLINE)
                        .collect(Collectors.toList()))
                .build();
    }

    @GetMapping("/searchCourseByName")
    public ApiResponse<List<Course>> searchCourseByName(@RequestParam String courseName) {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.searchCoursesByName(courseName))
                .build();
    }

}
