package com.hutech.easylearning.dto.reponse;


import com.hutech.easylearning.entity.Feedback;
import com.hutech.easylearning.entity.LearningOutcomes;
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
    BigDecimal coursePriceDiscount;
    String totalLearningTime;
    int totalFeedback;
    int averageRating;
    List<LearningOutcomes> learningOutcomes;
    List<CourseEventResponse> courseEventResponses;
    List<FeedbackInfoResponse> feedFeedbackInfoResponses;
}
