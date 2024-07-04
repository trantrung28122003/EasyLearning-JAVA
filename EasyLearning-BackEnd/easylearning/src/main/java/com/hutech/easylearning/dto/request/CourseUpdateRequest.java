package com.hutech.easylearning.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseUpdateRequest {
    String courseName;

    String courseDescription;

    BigDecimal coursePrice;

    String requirements;

    String courseType;

    String courseContent;

    String currentImageUrl;

    String newImageUrl;

    String instructor;

    LocalDateTime startDate;

    LocalDateTime endDate;

    LocalDateTime registrationDeadline;

    List<String> categories;

    int maxAttendees;

    int registeredUsers;

    LocalDateTime dateCreate;

    LocalDateTime dateChange;

    String changedBy;
}
