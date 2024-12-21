package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.ExerciseResponse;
import com.hutech.easylearning.dto.reponse.TrainingPartProgressResponse;
import com.hutech.easylearning.dto.reponse.UserTrainingProgressStatusResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.ScoreRequest;
import com.hutech.easylearning.entity.ExerciseQuestion;
import com.hutech.easylearning.entity.UserTrainingProgress;
import com.hutech.easylearning.service.ExerciseQuestionService;
import com.hutech.easylearning.service.UserTrainingProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userTrainingProgress")
public class UserTrainingProgressController {

    @Autowired
    private UserTrainingProgressService userTrainingProgressService;

    @Autowired
    private ExerciseQuestionService exerciseQuestionService;

    @GetMapping("/getUserTrainingProgress/{courseId}")
    public ApiResponse<UserTrainingProgressStatusResponse> getUserTrainingProgressByCourse(@PathVariable("courseId") String courseId) {
        return ApiResponse.<UserTrainingProgressStatusResponse>builder()
                .result(userTrainingProgressService.getUserTrainingProgressByCourse(courseId))
                .build();
    }

//    @PostMapping("/updateCompletionStatus/{trainingPartId}")
//    public ApiResponse<UserTrainingProgress> updateCompletionStatus(@PathVariable("trainingPartId") String trainingPartId) {
//        UserTrainingProgress updatedProgress = userTrainingProgressService.updateCompletionStatus(trainingPartId);
//        return ApiResponse.<UserTrainingProgress>builder()
//                .result(updatedProgress)
//                .build();
//    }

    @GetMapping("/getExercise/{trainingPartId}")
    public ApiResponse<List<ExerciseResponse>> getExerciseByTrainingPart(@PathVariable("trainingPartId") String trainingPartId) {
        return ApiResponse.<List<ExerciseResponse>>builder()
                .result(exerciseQuestionService.getExerciseByTrainingPart(trainingPartId))
                .build();
    }
    @GetMapping("/getUserTrainingProgressByPart/{trainingPartId}")
    public ApiResponse<UserTrainingProgress> getUserTrainingProgressByPart(@PathVariable("trainingPartId") String trainingPartId) {
        return ApiResponse.<UserTrainingProgress>builder()
                .result(userTrainingProgressService.getUserTrainingProgressByTrainingPart(trainingPartId))
                .build();
    }

    @PostMapping("/updateStatusPartProgress/{trainingPartId}")
    public ApiResponse<TrainingPartProgressResponse> updateStatusPartProgress(@PathVariable("trainingPartId") String trainingPartId, @RequestBody(required = false) ScoreRequest scoreRequest ) {

        return ApiResponse.<TrainingPartProgressResponse>builder()
                .result(userTrainingProgressService.updatePartProgress(trainingPartId, scoreRequest))
                .build();
    }
}

