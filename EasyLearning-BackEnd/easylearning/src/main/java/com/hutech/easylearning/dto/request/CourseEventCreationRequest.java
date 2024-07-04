package com.hutech.easylearning.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.enums.CourseEventType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseEventCreationRequest {

    String eventName;

    String eventType;

    String location;

    LocalDateTime dateStart;

    LocalDateTime dateEnd;

    LocalDateTime dateCreate;

    LocalDateTime dateChange;

    String changedBy;

    boolean isDeleted;
}
