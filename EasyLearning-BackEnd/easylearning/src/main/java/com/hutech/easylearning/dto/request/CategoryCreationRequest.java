package com.hutech.easylearning.dto.request;

import com.hutech.easylearning.entity.CourseDetail;
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
public class CategoryCreationRequest {

    String categoryName;

    String imageLink;

    Integer sortOrder;

    LocalDateTime dateCreate;

    LocalDateTime dateChange;

    String changedBy;

    boolean isDeleted;
}
