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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ApiResponse<Course> createCourse(@Valid CourseCreationRequest request,
                                            @RequestParam(value = "file", required = false) MultipartFile file) {
    return ApiResponse.<Course>builder()
                .result(courseService.createCourse(request, file))
                .build();
    }

    @PutMapping("/update/{courseId}")
    public ApiResponse<Course> updateCourse(@PathVariable("courseId") String courseId ,@RequestBody @Valid CourseUpdateRequest request,  @RequestParam(value = "file", required = false) MultipartFile file) {
        return ApiResponse.<Course>builder()
                .result(courseService.updateCourse(courseId, request, file))
                .build();
    }

    @DeleteMapping("/delete/{courseId}")
    public String deleteCourse(@PathVariable("courseId") String courseId) {
        courseService.deleteCourse(courseId);
        return "Course has been deleted";
    }

    @PostMapping("/softDelete/{courseId}")
    public String softDeleteCourse(@PathVariable("courseId") String courseId) {
        courseService.softDeleteCourse(courseId);
        return "course has been soft deleted";
    }

    @PostMapping("/restore/{courseId}")
    public String restoreCourse(@PathVariable("courseId") String courseId) {
        courseService.restoreCourse(courseId);
        return "Course has been restored";
    }
}
