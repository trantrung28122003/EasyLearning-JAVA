package com.hutech.easylearning.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseEventUpdateRequest {

    String eventName;

    String eventType;

    String location;

    LocalDateTime dateStart;

    LocalDateTime dateEnd;

    LocalDateTime dateCreate;

    LocalDateTime dateChange;

    boolean isDeleted;
}
