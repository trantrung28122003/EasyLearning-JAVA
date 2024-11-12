package com.hutech.easylearning.controller;

import com.hutech.easylearning.dto.reponse.CategoryWithCourseResponse;
import com.hutech.easylearning.dto.reponse.FeedbackResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.entity.Feedback;
import com.hutech.easylearning.service.CategoryService;
import com.hutech.easylearning.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedBackController {

    @Autowired
    private FeedbackService feedbackService;


    @GetMapping("/getAllFeedbackWithCourse/{courseId}")
    ApiResponse<FeedbackResponse> getAllFeedbackWithCourse(@PathVariable("courseId") String courseId) {
        return ApiResponse.<FeedbackResponse>builder()
                .result(feedbackService.getFeedbacksByCourse(courseId))
                .build();
    }
}
