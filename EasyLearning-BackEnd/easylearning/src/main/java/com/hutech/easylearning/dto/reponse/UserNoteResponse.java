package com.hutech.easylearning.dto.reponse;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseEvent;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNoteResponse {
    String id;
    String noteContent;
    BigDecimal timeStamp;
    String courseId;
    String trainingPartId;
    String trainingPartName;
    String courseEventName;
}
