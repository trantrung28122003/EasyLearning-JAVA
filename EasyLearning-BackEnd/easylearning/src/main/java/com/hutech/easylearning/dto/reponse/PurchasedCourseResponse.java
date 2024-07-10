package com.hutech.easylearning.dto.reponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    int totalTrainingPartByCourse;
    List<CourseEventResponse> courseEventResponses;
}
