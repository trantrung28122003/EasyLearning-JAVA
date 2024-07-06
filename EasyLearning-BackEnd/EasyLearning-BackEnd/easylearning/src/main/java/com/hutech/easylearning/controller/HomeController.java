package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.RoleResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.RoleRequest;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.Feedback;
import com.hutech.easylearning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/topThreeMostRegisteredCourses")
    ApiResponse<List<Course>> topThreeCourse() {
        return ApiResponse.<List<Course>>builder()
                .result(courseService.getTopThreeMostRegisteredCourses())
                .build();
    }
}
