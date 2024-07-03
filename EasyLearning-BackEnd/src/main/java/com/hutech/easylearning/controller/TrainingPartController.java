package com.hutech.easylearning.controller;

import com.hutech.easylearning.dto.request.*;
import com.hutech.easylearning.entity.CourseEvent;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.service.TrainingPartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trainingPart")
public class TrainingPartController {

    @Autowired
    private TrainingPartService trainingPartService;

    @GetMapping
    public List<TrainingPart> getAllTrainingPart() {
        List<TrainingPart> trainingParts = trainingPartService.getAllTrainingParts();
        return trainingParts.stream()
                .filter(trainingPart -> !trainingPart.isDeleted())
                .collect(Collectors.toList());
    }

    @GetMapping("/{courseId}")
    public List<TrainingPart> getTrainingPartsByCourse(@PathVariable("courseId") String courseId) {
        List<TrainingPart> trainingParts = trainingPartService.getTrainingPartsByCourseId(courseId);
        return trainingParts.stream()
                .filter(trainingPart -> !trainingPart.isDeleted())
                .collect(Collectors.toList());
    }


    @PostMapping("/create/{courseId}")
    public ApiResponse<TrainingPart> createTrainingPart(@PathVariable("courseId") String courseId, @RequestBody @Valid TrainingPartCreationRequest request) {
        return ApiResponse.<TrainingPart>builder()
                .result(trainingPartService.createTrainingPart(request, courseId))
                .build();
    }

    @PutMapping("/update/{trainingPartId}")
    public ApiResponse<TrainingPart> updateTrainingPart(@PathVariable("trainingPartId") String trainingPartId ,@RequestBody @Valid TrainingPartUpdateRequest request) {
        return ApiResponse.<TrainingPart>builder()
                .result(trainingPartService.updateTrainingPart(trainingPartId, request))
                .build();
    }

    @DeleteMapping("/delete/{trainingPartId}")
    public String deleteTrainingPart(@PathVariable("trainingPartId") String trainingPartId) {
        trainingPartService.deleteTrainingPart(trainingPartId);
        return "TrainingPart has been deleted";
    }

    @PostMapping("/softDelete/{trainingPartId}")
    public String softDeleteTrainingPart(@PathVariable("trainingPartId") String trainingPartId) {
        trainingPartService.softDeleteTrainingPart(trainingPartId);
        return "TrainingPart has been soft deleted";
    }

}
