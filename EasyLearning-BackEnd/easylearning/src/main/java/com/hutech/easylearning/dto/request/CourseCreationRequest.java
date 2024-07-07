package com.hutech.easylearning.dto.request;

import com.hutech.easylearning.entity.*;
import com.hutech.easylearning.enums.CourseType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseCreationRequest {

    String courseName;

    String courseDescription;

    BigDecimal coursePrice;

    String requirements;

    String courseType;

    String courseContent;

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

    boolean isDeleted;
}

