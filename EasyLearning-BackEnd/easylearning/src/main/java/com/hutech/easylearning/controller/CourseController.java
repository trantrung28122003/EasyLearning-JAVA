package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.UserResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.CourseCreationRequest;
import com.hutech.easylearning.dto.request.CourseUpdateRequest;
import com.hutech.easylearning.dto.request.UserUpdateRequest;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return courses.stream()
                .filter(course -> !course.isDeleted())
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ApiResponse<Course> createCourse(@RequestBody @Valid CourseCreationRequest request) {
        return ApiResponse.<Course>builder()
                .result(courseService.createCourse(request))
                .build();
    }

    @PutMapping("/update/{courseId}")
    public ApiResponse<Course> updateCourse(@PathVariable("courseId") String courseId ,@RequestBody @Valid CourseUpdateRequest request) {
        return ApiResponse.<Course>builder()
                .result(courseService.updateCourse(courseId, request))
                .build();
    }



}
