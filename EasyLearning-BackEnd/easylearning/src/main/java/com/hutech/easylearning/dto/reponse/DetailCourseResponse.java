package com.hutech.easylearning.dto.reponse;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailCourseResponse {
    String courseId;
    String courseName;
    String courseImage;
    String nameInstructor;
    BigDecimal coursePrice;
    int totalFeedback;
    int averageRating;
    List<CourseEventResponse> courseEventResponses;


}
