package com.hutech.easylearning.dto.reponse;

import com.hutech.easylearning.entity.LearningOutcomes;
import com.hutech.easylearning.enums.CourseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchasedCourseResponse {
    String courseId;
    String courseName;
    String courseImage;
    String instructor;
    LocalDateTime startDate;
    LocalDateTime endDate;
    CourseType courseType;
    int completedPartsByCourse;
    int totalTrainingPartByCourse;
    List<CourseEventResponse> courseEventResponses;
}
