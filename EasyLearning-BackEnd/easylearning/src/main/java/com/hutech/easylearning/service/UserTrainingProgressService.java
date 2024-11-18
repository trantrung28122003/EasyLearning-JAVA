package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.CompletedTrainingPartsResponse;
import com.hutech.easylearning.dto.request.UserTrainingProgressUpdateRequest;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.entity.UserTrainingProgress;
import com.hutech.easylearning.repository.UserTrainingProgressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserTrainingProgressService {

    @Autowired
    private UserTrainingProgressRepository userTrainingProgressRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TrainingPartService trainingPartService;

    @Transactional
    public UserTrainingProgress createUserTrainingProgress(UserTrainingProgress userTrainingProgress) {
        return userTrainingProgressRepository.save(userTrainingProgress);
    }

    @Transactional
    public UserTrainingProgress updateUserTrainingProgress(String id, UserTrainingProgressUpdateRequest request) {
        UserTrainingProgress userTrainingProgressById = userTrainingProgressRepository.findById(id).orElseThrow();
        var currentUserInfo  =  userService.getMyInfo();

        userTrainingProgressById.setWatchedDuration(request.getWatchedDuration());
        userTrainingProgressById.setQuizScore(request.getQuizScore());
        userTrainingProgressById.setCompleted(request.isCompleted());
        userTrainingProgressById.setDateChange(LocalDateTime.now());
        userTrainingProgressById.setChangedBy(currentUserInfo.getId());
        return userTrainingProgressRepository.save(userTrainingProgressById);
    }


    @Transactional
    public CompletedTrainingPartsResponse  getCompletedTrainingPartsOnCourses(String courseId) {
        var currentUserInfo  =  userService.getMyInfo();
        List<UserTrainingProgress> userTrainingProgressList = new ArrayList<>();
        List<TrainingPart> trainingPartList = trainingPartService.getTrainingPartsByCourseId(courseId);

        for(var trainingPart : trainingPartList)
        {
            UserTrainingProgress userTrainingProgress = userTrainingProgressRepository.findByUserIdAndTrainingPartId(currentUserInfo.getId(), trainingPart.getId());
            userTrainingProgressList.add(userTrainingProgress);
        }

        int completedTrainingParts = 0;
        for (UserTrainingProgress progress : userTrainingProgressList) {
            if (progress.isCompleted()) {
                completedTrainingParts++;
            }
        }
        CompletedTrainingPartsResponse  completedTrainingPartsResponse  = new CompletedTrainingPartsResponse().builder()
                .totalTrainingParts(trainingPartList.toArray().length)
                .totalCompletedTrainingParts(completedTrainingParts)
                .build();
        return completedTrainingPartsResponse;
    }

    public CompletedTrainingPartsResponse getCompletedTrainingPartsOnCourseEvent(String courseEventId) {
        var currentUserInfo  =  userService.getMyInfo();
        List<UserTrainingProgress> userTrainingProgressList = new ArrayList<>();
        List<TrainingPart> trainingPartList = trainingPartService.getTrainingPartsByCourseEventId(courseEventId);
        for(var trainingPart : trainingPartList)
        {
            UserTrainingProgress userTrainingProgress = userTrainingProgressRepository.findByUserIdAndTrainingPartId(currentUserInfo.getId(), trainingPart.getId());
            userTrainingProgressList.add(userTrainingProgress);
        }

        int completedTrainingParts = 0;
        for (UserTrainingProgress progress : userTrainingProgressList) {
            if (progress.isCompleted()) {
                completedTrainingParts++;
            }
        }
        CompletedTrainingPartsResponse completedTrainingPartsResponse = new CompletedTrainingPartsResponse().builder()
                .totalTrainingParts(trainingPartList.size())
                .totalCompletedTrainingParts(completedTrainingParts)
                .build();
        return completedTrainingPartsResponse;
    }




}
