package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.DeletedInfoResponse;
import com.hutech.easylearning.dto.reponse.RestoreRequest;
import com.hutech.easylearning.entity.CourseDetail;
import com.hutech.easylearning.enums.DeletedInfoType;
import com.hutech.easylearning.repository.CourseRepository;
import com.hutech.easylearning.repository.TrainingPartRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.ComponentInputMapUIResource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletedRecordsService {

    @Autowired
    CourseService courseService;

    @Autowired
    private TrainingPartService trainingPartService;

    @Autowired
    private CourseEventService courseEventService;

    public List<DeletedInfoResponse> getAllSoftDelete() {
        List<DeletedInfoResponse> deletedInfoResponses = new ArrayList<DeletedInfoResponse>();

        for(var course : courseService.getAllCourses().stream()
                .filter(course -> course.isDeleted()).collect(Collectors.toList()))
        {
            Duration duration = Duration.between(course.getDateChange(), LocalDateTime.now());
            long daysSinceChanged = duration.toDays();

            if (daysSinceChanged > 90) {
                courseService.deleteCourse(course.getId());
            }
            else {
                DeletedInfoResponse deletedInfoResponseCourse = DeletedInfoResponse.builder()
                        .id(course.getId())
                        .name(course.getCourseName())
                        .deletedDate(course.getDateChange())
                        .type(DeletedInfoType.COURSE)
                        .detail("khóa học đã được xóa " + daysSinceChanged + "ngày")
                        .build();
                deletedInfoResponses.add(deletedInfoResponseCourse);
            }
        }

        for (var trainingPart : trainingPartService.getAllTrainingParts().stream()
                .filter(trainingPart -> trainingPart.isDeleted())
                .collect(Collectors.toList())) {
            Duration duration = Duration.between(trainingPart.getDateChange(), LocalDateTime.now());
            long daysSinceChanged = duration.toDays();

            if (daysSinceChanged > 90) {
                trainingPartService.deleteTrainingPart(trainingPart.getId());
            }
            else {
                DeletedInfoResponse deletedInfoResponseTrainingPart = DeletedInfoResponse.builder()
                        .id(trainingPart.getId())
                        .name(trainingPart.getTrainingPartName())
                        .deletedDate(trainingPart.getDateChange())
                        .type(DeletedInfoType.TRAINING_PART)
                        .detail("Phần học này đã được xóa " + daysSinceChanged + "ngày")
                        .build();
                deletedInfoResponses.add(deletedInfoResponseTrainingPart);
            }
        }

        for(var courseEvent : courseEventService.getAllCourseEvents().stream()
                .filter(courseEvent -> courseEvent.isDeleted())
                .collect(Collectors.toList()))
        {
            Duration duration = Duration.between(courseEvent.getDateChange(), LocalDateTime.now());
            long daysSinceChanged = duration.toDays();
            if (daysSinceChanged > 90) {
                courseEventService.deleteCourseEvent(courseEvent.getId());
            }
            else {
                DeletedInfoResponse deletedInfoResponseCourseEvent = DeletedInfoResponse.builder()
                        .id(courseEvent.getId())
                        .name(courseEvent.getEventName())
                        .deletedDate(courseEvent.getDateChange())
                        .type(DeletedInfoType.EVENT)
                        .detail("Buổi học này đã được xóa " + daysSinceChanged + "ngày")
                        .build();
                deletedInfoResponses.add(deletedInfoResponseCourseEvent);
            }
        }
        return deletedInfoResponses;
    }

    public void restoreDeletedRecord(RestoreRequest restoreRequest)
    {
        DeletedInfoType type = DeletedInfoType.valueOf(restoreRequest.getType().toUpperCase());
        switch (type) {
            case COURSE:
                courseService.restoreCourse(restoreRequest.getId());
                break;
            case TRAINING_PART:
                trainingPartService.restoreTrainingPart(restoreRequest.getId());
                break;
            case EVENT:
                courseEventService.restoreCourseEvent(restoreRequest.getId());
                break;
            default:
                throw new IllegalArgumentException("Không có: " + type);
        }
    }

}
