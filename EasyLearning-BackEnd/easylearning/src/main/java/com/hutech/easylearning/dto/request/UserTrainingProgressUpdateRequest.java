package com.hutech.easylearning.dto.request;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTrainingProgressUpdateRequest {
    int progress;

    Integer watchedDuration;

    Integer quizScore;

    boolean isCompleted;

}
