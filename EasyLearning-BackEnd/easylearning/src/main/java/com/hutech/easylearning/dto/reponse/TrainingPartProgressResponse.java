package com.hutech.easylearning.dto.reponse;

import com.hutech.easylearning.enums.TrainingPartType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingPartProgressResponse {

    String id;

    String trainingPartName;

    LocalDateTime startTime;

    LocalDateTime endTime;
    LocalDateTime dateChange;

    String description;

    @Enumerated(EnumType.STRING)
    TrainingPartType trainingPartType;

    String imageUrl;

    String videoUrl;

    boolean isFree;

    String courseId;

    String courseEventId;

    boolean isDeleted;

    boolean completed;

    double watchedDuration;

    double quizScore;
}
