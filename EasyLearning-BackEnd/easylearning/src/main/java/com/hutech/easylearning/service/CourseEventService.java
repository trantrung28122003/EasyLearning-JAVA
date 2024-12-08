package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.reponse.CourseEventResponse;
import com.hutech.easylearning.dto.reponse.ScheduleDetailEventResponse;
import com.hutech.easylearning.dto.request.CourseCreationRequest;
import com.hutech.easylearning.dto.request.CourseEventCreationRequest;
import com.hutech.easylearning.dto.request.CourseEventUpdateRequest;
import com.hutech.easylearning.entity.Category;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseEvent;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.enums.CourseEventType;
import com.hutech.easylearning.repository.CourseEventRepository;
import com.hutech.easylearning.repository.CourseRepository;
import com.hutech.easylearning.repository.TrainingPartRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class CourseEventService {

    @Autowired
    CourseEventRepository courseEventRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;

    @Autowired
    TrainingPartService trainingPartService;
    @Autowired
     TrainingPartRepository trainingPartRepository;

    @Autowired
    UserTrainingProgressService userTrainingProgressService;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<CourseEvent> getAllCourseEvents() {
        return courseEventRepository.findAll();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public CourseEvent getCourseEventById(String courseEventId) {
        return courseEventRepository.findById(courseEventId)
                .orElseThrow(() -> new RuntimeException("Course Event not found with id: " + courseEventId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public CourseEvent createCourseEvent(CourseEventCreationRequest request) {
        var currentUserInfo = userService.getMyInfo();
        CourseEvent courseEvent = CourseEvent.builder()
                .eventName(request.getEventName())
                .eventType(CourseEventType.valueOf(request.getEventType()))
                .location(request.getLocation())
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUserInfo.getId())
                .isDeleted(false)
                .build();
        return courseEventRepository.save(courseEvent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public CourseEvent updateCourseEvent(String courseEventId, CourseEventUpdateRequest request)
    {
        var currentUserInfo = userService.getMyInfo();
        var getCourseEventById = courseEventRepository.findById(courseEventId)
                .orElseThrow(() -> new RuntimeException("CourseEVent not found with id: " + courseEventId));
        getCourseEventById.setEventName(request.getEventName());
        getCourseEventById.setEventType(CourseEventType.valueOf(request.getEventType()));
        getCourseEventById.setLocation(request.getLocation());
        getCourseEventById.setDateStart(request.getDateStart());
        getCourseEventById.setDateEnd(request.getDateEnd());
        getCourseEventById.setDateChange(request.getDateChange());
        getCourseEventById.setChangedBy(currentUserInfo.getId());
        return courseEventRepository.save(getCourseEventById);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteCourseEvent(String courseEventId) {
        trainingPartService.deleteTrainingPartByCourseEventId(courseEventId);
        courseEventRepository.deleteById(courseEventId);
    }

    @Transactional
    public void softDeleteCourseEvent(String courseEventId) {
        trainingPartService.softDeleteTrainingPartByCourseEventId(courseEventId);
        CourseEvent courseEvent = getCourseEventById(courseEventId);
        courseEvent.setDeleted(true);
        courseEvent.setDateChange(LocalDateTime.now());
        courseEventRepository.save(courseEvent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void restoreCourseEvent(String courseEventId) {
        trainingPartService.restoreTrainingPartByCourseEventId(courseEventId);
        CourseEvent courseEvent = getCourseEventById(courseEventId);
        courseEvent.setDeleted(false);
        courseEvent.setDateChange(LocalDateTime.now());
        courseEventRepository.save(courseEvent);
    }

    public ScheduleDetailEventResponse getDetailCourseEvent (String courseEventId)
    {
        String nameInstructor;
        var getCourseEventById = courseEventRepository.findById(courseEventId)
                .orElseThrow(() -> new RuntimeException("CourseEVent not found with id: " + courseEventId));
        var trainingParts = trainingPartRepository.findTrainingPartByCourseEventId(courseEventId).stream()
                .sorted(Comparator.comparing(TrainingPart::getStartTime))
                .collect(Collectors.toList());;
        var course = courseRepository.findById(trainingParts.get(0).getCourseId());

        nameInstructor = course.get().getInstructor();

        ScheduleDetailEventResponse scheduleDetailEventResponse = ScheduleDetailEventResponse.builder()
                .nameInstructor(nameInstructor)
                .startDate(getCourseEventById.getDateStart())
                .trainingParts(trainingParts)
                .build();
        return scheduleDetailEventResponse;
    }

    public List<CourseEventResponse> getCourseEventsByCourse (String courseId)
    {
        var trainingPartByCourse = trainingPartRepository.findTrainingPartByCourseId(courseId);

        List<CourseEventResponse> courseEventResponses = new ArrayList<>();
        for(var trainingPartId : trainingPartByCourse)
        {
            for(var courseEvent : courseEventRepository.findAll())
            {
                if(courseEvent.getId().equals(trainingPartId.getCourseEventId())) {
                    CourseEventResponse courseEventResponse = CourseEventResponse.builder()
                            .id(courseEvent.getId())
                            .courseEventName(courseEvent.getEventName())
                            .startTime(courseEvent.getDateStart())
                            .endTime(courseEvent.getDateEnd())
                            .location(courseEvent.getLocation())
                            .build();
                    if (courseEventResponses.stream()
                            .noneMatch(existing -> existing.getId().equals(courseEventResponse.getId()))){
                        var totalTrainingPartByCourseEvent = trainingPartRepository.findTrainingPartByCourseEventId(courseEventResponse.getId()).size();
                        var completePartsByCourseEvent = userTrainingProgressService.getCompletedTrainingPartsOnCourseEvent(courseEventResponse.getId());
                        courseEventResponse.setCompletedPartsByCourseEvent(completePartsByCourseEvent);
                        courseEventResponse.setTotalPartsByCourseEvent(totalTrainingPartByCourseEvent);
                        courseEventResponses.add(courseEventResponse);
                    }
                }
            }
        }
        return  courseEventResponses;
    }
}