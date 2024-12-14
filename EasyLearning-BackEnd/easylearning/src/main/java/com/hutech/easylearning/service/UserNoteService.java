package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.UserNoteResponse;
import com.hutech.easylearning.dto.request.UserNoteCreationRequest;
import com.hutech.easylearning.dto.request.UserNoteUpdateRequest;
import com.hutech.easylearning.entity.CourseEvent;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.entity.UserNote;
import com.hutech.easylearning.repository.TrainingPartRepository;
import com.hutech.easylearning.repository.UserNoteRepository;
import com.hutech.easylearning.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserNoteService {

    private final UserNoteRepository userNoteRepository;
    private final UserService userService;
    private final TrainingPartRepository trainingPartRepository;

    public UserNoteService(UserRepository userRepository, UserNoteRepository userNoteRepository, UserService userService, TrainingPartRepository trainingPartRepository) {

        this.userNoteRepository = userNoteRepository;
        this.userService = userService;
        this.trainingPartRepository = trainingPartRepository;
    }

    public List<UserNoteResponse> getNotesByCourseIdAndUserId (String courseId)
    {
        var currentUser = userService.getMyInfo();
        List<UserNoteResponse> userNoteResponseList = new ArrayList<>();
        List<UserNote> userNoteList = userNoteRepository.findAllByCourseIdAndUserId(courseId, currentUser.getId());

        for (UserNote userNote : userNoteList) {
            CourseEvent courseEventByTrainingPartId = trainingPartRepository.findCourseEventByTrainingPartId(userNote.getTrainingPartId());
            TrainingPart trainingPart = trainingPartRepository.findById(userNote.getTrainingPartId()).orElse(null);
            UserNoteResponse userNoteResponse = UserNoteResponse.builder()
                    .id(userNote.getId())
                    .noteContent(userNote.getNoteContent())
                    .trainingPartName(trainingPart.getTrainingPartName())
                    .trainingPartId(trainingPart.getId())
                    .timeStamp(userNote.getTimeStamp())
                    .courseEventName(courseEventByTrainingPartId.getEventName())
                    .build();
            userNoteResponseList.add(userNoteResponse);
        }
        return userNoteResponseList;
    }

    public UserNoteResponse addUserNote (UserNoteCreationRequest request)
    {
        var currentUser = userService.getMyInfo();
        UserNote userNote = UserNote.builder()
                .noteContent(request.getNoteContent())
                .timeStamp(request.getTimestamp())
                .userId(currentUser.getId())
                .courseId(request.getCourseId())
                .trainingPartId(request.getTrainingPartId())
                .changedBy(currentUser.getId())
                .isDeleted(false)
                .dateChange(LocalDateTime.now())
                .dateCreate(LocalDateTime.now())
                .build();
        userNoteRepository.save(userNote);

        CourseEvent courseEventByTrainingPartId = trainingPartRepository.findCourseEventByTrainingPartId(userNote.getTrainingPartId());
        TrainingPart trainingPart = trainingPartRepository.findById(userNote.getTrainingPartId()).orElse(null);
        UserNoteResponse userNoteResponse = UserNoteResponse.builder()
                .id(userNote.getId())
                .noteContent(userNote.getNoteContent())
                .trainingPartName(trainingPart.getTrainingPartName())
                .trainingPartId(trainingPart.getId())
                .timeStamp(userNote.getTimeStamp())
                .courseEventName(courseEventByTrainingPartId.getEventName())
                .build();
        return userNoteResponse;
    }

    public UserNoteResponse updateUserNote (UserNoteUpdateRequest request)
    {
        var currentUser = userService.getMyInfo();
        UserNote userNote = userNoteRepository.findById(request.getId()).orElse(null);
        userNote.setNoteContent(request.getNoteContent());
        userNoteRepository.save(userNote);

        CourseEvent courseEventByTrainingPartId = trainingPartRepository.findCourseEventByTrainingPartId(userNote.getTrainingPartId());
        TrainingPart trainingPart = trainingPartRepository.findById(userNote.getTrainingPartId()).orElse(null);
        UserNoteResponse userNoteResponse = UserNoteResponse.builder()
                .id(userNote.getId())
                .noteContent(userNote.getNoteContent())
                .trainingPartName(trainingPart.getTrainingPartName())
                .trainingPartId(trainingPart.getId())
                .timeStamp(userNote.getTimeStamp())
                .courseEventName(courseEventByTrainingPartId.getEventName())
                .build();
        return userNoteResponse;
    }

    @Transactional
    public void deleteNoteById(String id) {
        if (!userNoteRepository.existsById(id)) {
            throw new IllegalArgumentException("Ghi chú với ID: " + id + " không tồn tại!");
        }
        userNoteRepository.deleteById(id);
    }
}
