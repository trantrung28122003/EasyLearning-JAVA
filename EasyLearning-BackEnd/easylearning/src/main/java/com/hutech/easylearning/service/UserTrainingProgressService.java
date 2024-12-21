package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.CourseEventResponse;
import com.hutech.easylearning.dto.reponse.TrainingPartProgressResponse;
import com.hutech.easylearning.dto.reponse.UserTrainingProgressStatusResponse;
import com.hutech.easylearning.dto.request.ScoreRequest;
import com.hutech.easylearning.dto.request.UserTrainingProgressUpdateRequest;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.entity.UserTrainingProgress;
import com.hutech.easylearning.repository.CourseEventRepository;
import com.hutech.easylearning.repository.CourseRepository;
import com.hutech.easylearning.repository.TrainingPartRepository;
import com.hutech.easylearning.repository.UserTrainingProgressRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTrainingProgressService {
    final CourseRepository courseRepository;
    final UserTrainingProgressRepository userTrainingProgressRepository;
    final UserService userService;
    final TrainingPartService trainingPartService;
    final CourseEventRepository courseEventRepository;
    final TrainingPartRepository trainingPartRepository;

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
    public UserTrainingProgress updateCompletionStatus(String trainingPartId) {

        var currentUser = userService.getMyInfo();
        UserTrainingProgress userTrainingProgress = userTrainingProgressRepository.findByUserIdAndTrainingPartId(currentUser.getId(), trainingPartId);

        userTrainingProgress.setCompleted(true);
        userTrainingProgress.setDateChange(LocalDateTime.now());
        userTrainingProgress.setChangedBy(currentUser.getId());
        return userTrainingProgressRepository.save(userTrainingProgress);
    }

    @Transactional
    public TrainingPartProgressResponse updatePartProgress(String trainingPartId, ScoreRequest scoreRequest) {

        var currentUser = userService.getMyInfo();
        UserTrainingProgress userTrainingProgress = userTrainingProgressRepository.findByUserIdAndTrainingPartId(currentUser.getId(), trainingPartId);

        int score = 0;
        if (scoreRequest != null) {
            score = scoreRequest.getCorrectAnswersCount() + scoreRequest.getTotalQuestionsCount();
        }

        userTrainingProgress.setCompleted(true);
        userTrainingProgress.setQuizScore(score);
        userTrainingProgress.setDateChange(LocalDateTime.now());
        userTrainingProgress.setChangedBy(currentUser.getId());
        userTrainingProgressRepository.save(userTrainingProgress);
        Integer watchedDuration = (userTrainingProgress.getWatchedDuration() != null)
                ? userTrainingProgress.getWatchedDuration()
                : 0;
        TrainingPart trainingPartById = trainingPartRepository.findById(trainingPartId).orElseThrow();
        TrainingPartProgressResponse trainingPartProgressResponse = new TrainingPartProgressResponse().builder()
                .id(trainingPartById.getId())
                .trainingPartName(trainingPartById.getTrainingPartName())
                .trainingPartType(trainingPartById.getTrainingPartType())
                .startTime(trainingPartById.getStartTime())
                .endTime(trainingPartById.getEndTime())
                .dateChange(trainingPartById.getDateChange())
                .isFree(trainingPartById.isFree())
                .imageUrl(trainingPartById.getImageUrl())
                .videoUrl(trainingPartById.getVideoUrl())
                .watchedDuration(watchedDuration)
                .quizScore(userTrainingProgress.getQuizScore())

                .completed(userTrainingProgress.isCompleted())

                .build();
        return trainingPartProgressResponse;
    }


    @Transactional
    public int  getCompletedTrainingPartsOnCourses(String courseId) {
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
        return completedTrainingParts;
    }

    int  getCompletedTrainingPartsOnCourseEvent(String courseEventId) {
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
            if (progress != null && progress.isCompleted()) {
                completedTrainingParts++;
            }
        }
        return completedTrainingParts;
    }


    @Transactional
    public UserTrainingProgressStatusResponse getUserTrainingProgressByCourse(String courseId) {
        var currentUserInfo = userService.getMyInfo();
        int completedTrainingParts = 0;
        int totalTrainingParts = 0;
        var course = courseRepository.findById(courseId).orElseThrow();

        List<TrainingPart> trainingPartList = trainingPartService.getTrainingPartsByCourseId(courseId);
        completedTrainingParts = getCompletedTrainingPartsOnCourses(courseId);
        totalTrainingParts = trainingPartList.size();
        List<CourseEventResponse> courseEventResponseList = new ArrayList<>();
        for(var trainingPart : trainingPartList)
        {
            for(var courseEvent : courseEventRepository.findAll())
            {
                if(courseEvent.getId().equals(trainingPart.getCourseEventId())) {
                    CourseEventResponse courseEventResponse = CourseEventResponse.builder()
                            .id(courseEvent.getId())
                            .courseEventName(courseEvent.getEventName())
                            .startTime(courseEvent.getDateStart())
                            .endTime(courseEvent.getDateEnd())
                            .location(courseEvent.getLocation())
                            .build();

                    if (courseEventResponseList.stream()
                            .noneMatch(existing -> existing.getId().equals(courseEventResponse.getId()))){
                        var trainingPartByCourseEvent = trainingPartRepository.findTrainingPartByCourseEventId(courseEventResponse.getId()).stream()
                                .sorted(Comparator.comparing(TrainingPart::getStartTime))
                                .collect(Collectors.toList());

                        List<TrainingPartProgressResponse> userTrainingProgressList = new ArrayList<>();

                        for (var trainingpartByCourseEvent : trainingPartByCourseEvent) {
                            UserTrainingProgress userTrainingProgress = userTrainingProgressRepository.findByUserIdAndTrainingPartId(currentUserInfo.getId(), trainingpartByCourseEvent.getId());
                            Integer watchedDuration = (userTrainingProgress.getWatchedDuration() != null)
                                    ? userTrainingProgress.getWatchedDuration()
                                    : 0;
                            Integer quizScore = (userTrainingProgress.getQuizScore() != null)
                                    ? userTrainingProgress.getQuizScore()
                                    : 0;

                            TrainingPartProgressResponse trainingPartProgressResponse = new TrainingPartProgressResponse().builder()
                                    .id(trainingpartByCourseEvent.getId())
                                    .trainingPartName(trainingpartByCourseEvent.getTrainingPartName())
                                    .trainingPartType(trainingpartByCourseEvent.getTrainingPartType())
                                    .startTime(trainingpartByCourseEvent.getStartTime())
                                    .endTime(trainingpartByCourseEvent.getEndTime())
                                    .dateChange(trainingPart.getDateChange())
                                    .isFree(trainingpartByCourseEvent.isFree())
                                    .imageUrl(trainingpartByCourseEvent.getImageUrl())
                                    .videoUrl(trainingpartByCourseEvent.getVideoUrl())
                                    .watchedDuration(watchedDuration)
                                    .quizScore(quizScore)

                                    .completed(userTrainingProgress.isCompleted())

                                    .build();
                            userTrainingProgressList.add(trainingPartProgressResponse);
                        }

                        var totalTrainingPartByCourseEvent = trainingPartByCourseEvent.size();
                        courseEventResponse.setTotalPartsByCourseEvent(totalTrainingPartByCourseEvent);
                        courseEventResponse.setTrainingPartProgressResponses(userTrainingProgressList);
                        courseEventResponse.setTrainingParts(trainingPartByCourseEvent);
                        courseEventResponseList.add(courseEventResponse);
                    }
                }
            }

        }
        Collections.sort(courseEventResponseList, Comparator.comparing(CourseEventResponse::getStartTime));
        UserTrainingProgressStatusResponse userTrainingProgressStatusResponse = new UserTrainingProgressStatusResponse().builder()
                .courseName(course.getCourseName())
                .courseInstructor(course.getInstructor())
                .completedPartsByCourse(completedTrainingParts)
                .totalPartsByCourse(totalTrainingParts)
                .courseEventsResponses(courseEventResponseList)
                .build();

        return userTrainingProgressStatusResponse;
    }
    @Transactional
    public UserTrainingProgress getUserTrainingProgressByTrainingPart(String trainingPartId) {

        var currentUser = userService.getMyInfo();
        UserTrainingProgress userTrainingProgress = userTrainingProgressRepository.findByUserIdAndTrainingPartId(currentUser.getId(), trainingPartId);
        return  userTrainingProgress;
    }

}
