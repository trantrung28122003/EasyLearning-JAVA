package com.hutech.easylearning.dto.request;



import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNoteCreationRequest {
    String noteContent;
    String courseId;
    String trainingPartId;
    BigDecimal timestamp;
}
