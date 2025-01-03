package com.hutech.easylearning.dto.reponse;


import com.hutech.easylearning.entity.TrainingPart;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleDetailEventResponse {
    LocalDateTime startDate;
    String nameInstructor;
    List<TrainingPart> trainingParts;
}
