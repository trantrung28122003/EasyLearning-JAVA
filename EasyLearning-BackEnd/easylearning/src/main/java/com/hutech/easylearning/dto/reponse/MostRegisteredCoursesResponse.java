package com.hutech.easylearning.dto.reponse;


import com.hutech.easylearning.entity.LearningOutcomes;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MostRegisteredCoursesResponse {
    String courseName;
    String courseImage;
    String nameInstructor;
    BigDecimal coursePrice;
    BigDecimal coursePriceDiscount;
    String totalLearningTime;
    int totalFeedback;
    int averageRating;
}
