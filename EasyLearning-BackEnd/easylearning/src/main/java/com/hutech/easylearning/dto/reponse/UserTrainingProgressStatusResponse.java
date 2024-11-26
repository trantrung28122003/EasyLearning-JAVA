package com.hutech.easylearning.dto.reponse;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTrainingProgressStatusResponse {
    String courseName;
    String courseInstructor;
    int completedPartsByCourse;
    int totalPartsByCourse;
    List<CourseEventResponse> courseEventsResponses;

}
