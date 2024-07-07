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
public class CategoryUpdateRequest {

    String categoryName;

    Integer sortOrder;

    LocalDateTime dateCreate;

    LocalDateTime dateChange;

    String changedBy;
}
