package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.request.TrainingPartCreationRequest;
import com.hutech.easylearning.dto.request.TrainingPartUpdateRequest;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseDetail;
import com.hutech.easylearning.entity.TrainerDetail;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.enums.CourseType;
import com.hutech.easylearning.enums.TrainingPartType;
import com.hutech.easylearning.repository.CourseEventRepository;
import com.hutech.easylearning.repository.TrainingPartRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainingPartService {

    TrainingPartRepository trainingPartRepository;

    CourseEventRepository courseEventRepository;

    @Autowired
    UserService userService;

    @Transactional(readOnly = true)
    public List<TrainingPart> getAllTrainingParts() {
        return trainingPartRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TrainingPart> getTrainingPartsByCourseId(String courseId) {
        return trainingPartRepository.findTrainingPartByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public TrainingPart getTrainingPartById(String id) {
        return trainingPartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TrainingPart not found with id: " + id));
    }

    @Transactional
    public TrainingPart createTrainingPart(TrainingPartCreationRequest request, String courseId) {
        if(request.getVideoUrl() != null) {
            //imageLink = fileService.saveFile(courseViewModel.getImage());
        }
        var currentUserInfo  =  userService.getMyInfo();
        TrainingPart trainingPart = TrainingPart.builder()
                .trainingPartName(request.getTrainingPartName())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .description(request.getDescription())
                .trainingPartType(TrainingPartType.valueOf(request.getTrainingPartType()))
                .videoUrl(request.getVideoUrl())
                .isFree(request.isFree())
                .courseId(courseId)
                .courseEventId(request.getCourseEventId())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUserInfo.getId())
                .isDeleted(false)
                .build();
        return trainingPartRepository.save(trainingPart);
    }

    @Transactional
    public TrainingPart updateTrainingPart(String trainingPartId, TrainingPartUpdateRequest request) {
        var trainingPartById = trainingPartRepository.findById(trainingPartId).orElseThrow(() -> new RuntimeException("TrainingPart not found with id: " + trainingPartId));
        var currentUserInfo  =  userService.getMyInfo();
        String videoUrl = request.getCurrentVideoUrl();
        if(request.getNewVideoUrl() != null) {
            //videoUrl = fileService.saveFile(courseViewModel.getImage());
        }
        trainingPartById.setTrainingPartName(request.getTrainingPartName());
        trainingPartById.setStartTime(request.getStartTime());
        trainingPartById.setEndTime(request.getEndTime());
        trainingPartById.setDescription(request.getDescription());
        trainingPartById.setTrainingPartType(TrainingPartType.valueOf(request.getTrainingPartType()));
        trainingPartById.setVideoUrl(videoUrl);
        trainingPartById.setFree(request.getFree());
        trainingPartById.setCourseId(request.getCourseId());
        trainingPartById.setCourseEventId(request.getCourseEventId());
        trainingPartById.setDateChange(LocalDateTime.now());
        trainingPartById.setChangedBy(currentUserInfo.getId());

        return trainingPartRepository.save(trainingPartById);
    }

    @Transactional
    public void deleteTrainingPart(String trainingPartId) {
        trainingPartRepository.deleteById(trainingPartId);
    }

    @Transactional
    public void softDeleteTrainingPart(String trainingPartId) {
        TrainingPart trainingPart = getTrainingPartById(trainingPartId);
        trainingPart.setDeleted(true);
        trainingPartRepository.save(trainingPart);
    }

    @Transactional
    public void deleteTrainingPartByCourseEventId(String courseEventId) {
        var getTrainingPartsByCourseEventId = trainingPartRepository.findTrainingPartByCourseEventId(courseEventId);
        for(var trainingPart : getTrainingPartsByCourseEventId)
        {
            trainingPartRepository.deleteById(trainingPart.getId());
        }
    }

    @Transactional
    public void softDeleteTrainingPartByCourseEventId(String courseEventId) {
        var getTrainingPartsByCourseEventId = trainingPartRepository.findTrainingPartByCourseEventId(courseEventId);
        for(var trainingPart : getTrainingPartsByCourseEventId)
        {
            trainingPart.setDeleted(true);
            trainingPartRepository.save(trainingPart);
        }
    }

}

