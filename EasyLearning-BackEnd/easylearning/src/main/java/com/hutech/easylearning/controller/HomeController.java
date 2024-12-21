package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.CategoryWithCourseResponse;
import com.hutech.easylearning.dto.reponse.DetailCourseResponse;
import com.hutech.easylearning.dto.reponse.FeedbackInfoResponse;
import com.hutech.easylearning.dto.reponse.FeedbackResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.entity.Category;
import com.hutech.easylearning.entity.Course;

import com.hutech.easylearning.service.CategoryService;
import com.hutech.easylearning.service.CourseService;
import com.hutech.easylearning.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/topFourMostRegisteredCourses")
    ApiResponse<List<Course>> topFourCourse() {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.getTopFourMostRegisteredCourses())
                .build();
    }

    @GetMapping("/topFourMostCategory")
    ApiResponse<List<Category>> getTop4CategoriesBySortOrder() {
        return ApiResponse.<List<Category>>builder()
                .result(categoryService.findTop4BySortOrderNotNull())
                .build();
    }

    @GetMapping("/getAllCourse")
    ApiResponse<List<Course>> getAllCourse() {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.getAllCourses())
                .build();
    }

    @GetMapping("/getAllCategoryWithCourse")
    ApiResponse<List<CategoryWithCourseResponse>> getAllCategoryWithCourse() {
        return ApiResponse.<List<CategoryWithCourseResponse>>builder()
                .result(categoryService.getAllCategoryWithCourse())
                .build();
    }

    @GetMapping("/getCoursesByCategory/{categoryId}")
    ApiResponse<CategoryWithCourseResponse> getCoursesByCategory(@PathVariable("categoryId") String categoryId) {
        return ApiResponse.<CategoryWithCourseResponse>builder()
                .result(categoryService.getCoursesByCategory(categoryId))
                .build();
    }

    @GetMapping("/getCourseWithDiscount")
    ApiResponse<List<Course>> getAllCourseWithDiscount() {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.getAllCourseWithDiscount())
                .build();
    }

    @GetMapping("/getCourseWithFree")
    ApiResponse<List<Course>> getCourseWithFree() {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.getCourseWithFree())
                .build();
    }


    @GetMapping("/detailCourse/{courseId}")
    public ApiResponse<DetailCourseResponse> getDetailCourse(@PathVariable("courseId") String courseId) {
        return ApiResponse.<DetailCourseResponse>builder()
                .result(courseService.getDetailCourse(courseId))
                .build();
    }

    @GetMapping("/getFeedbacksByCourseWithoutUser/{courseId}")
    ApiResponse<FeedbackResponse> getFeedbacksByCourseWithoutUser(@PathVariable("courseId") String courseId) {
        return ApiResponse.<FeedbackResponse>builder()
                .result(feedbackService.getFeedbacksByCourseWithoutUser(courseId))
                .build();
    }


    @GetMapping("/getFeedbacksWithFiveRating")
    ApiResponse<List<FeedbackInfoResponse>> getFeedbacksWithFiveRating() {
        return ApiResponse.<List<FeedbackInfoResponse>>builder()
                .result(feedbackService.getFeedbacksWithFiveRating())
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<Course>> searchCourses(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String courseType,
            @RequestParam(required = false) Integer rating
    ) {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.searchCourses(query, sortBy, courseType, rating))
                .build();
    }
}
