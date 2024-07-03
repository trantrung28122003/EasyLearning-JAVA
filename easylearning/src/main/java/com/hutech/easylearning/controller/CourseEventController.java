package com.hutech.easylearning.controller;

import com.hutech.easylearning.dto.request.*;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseEvent;
import com.hutech.easylearning.service.CourseEventService;
import com.hutech.easylearning.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courseEvent")
public class CourseEventController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseEventService courseEventService;

    @GetMapping
    public List<CourseEvent> getAllCourseEvent() {
        List<CourseEvent> courseEvents = courseEventService.getAllCourseEvents();
        return courseEvents.stream()
                .filter(course -> !course.isDeleted())
                .collect(Collectors.toList());
    }


    @PostMapping("/create")
    public ApiResponse<CourseEvent> createCourseEvent(@RequestBody @Valid CourseEventCreationRequest request) {
        return ApiResponse.<CourseEvent>builder()
                .result(courseEventService.createCourseEvent(request))
                .build();
    }

    @PutMapping("/update/{courseEventId}")
    public ApiResponse<CourseEvent> updateCourseEvent(@PathVariable("courseEventId") String courseEventId ,@RequestBody @Valid CourseEventUpdateRequest request) {
        return ApiResponse.<CourseEvent>builder()
                .result(courseEventService.updateCourseEvent(courseEventId, request))
                .build();
    }

    @DeleteMapping("/delete/{courseEventId}")
    public String deleteCourseEventId(@PathVariable("courseEventId") String courseEventId) {
        courseEventService.deleteCourseEvent(courseEventId);
        return "CourseEvent has been deleted";
    }

    @PostMapping("/softDelete/{courseEventId}")
    public String softDeleteCourseEventId(@PathVariable("courseEventId") String courseEventId) {
        courseEventService.softDeleteCourseEvent(courseEventId);
        return "CourseEvent has been soft deleted";
    }
}
