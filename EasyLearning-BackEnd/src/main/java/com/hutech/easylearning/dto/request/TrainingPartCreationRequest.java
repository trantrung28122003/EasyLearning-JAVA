package com.hutech.easylearning.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseEvent;
import com.hutech.easylearning.enums.TrainingPartType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingPartCreationRequest {

    String trainingPartName;

    LocalDateTime startTime;

    LocalDateTime endTime;

    String description;

    String trainingPartType;

    String imageUrl;

    String videoUrl;

    boolean isFree;

    String courseId;

    String courseEventId;

    LocalDateTime dateCreate;

    LocalDateTime dateChange;

    String changedBy;

    boolean isDeleted;
}
