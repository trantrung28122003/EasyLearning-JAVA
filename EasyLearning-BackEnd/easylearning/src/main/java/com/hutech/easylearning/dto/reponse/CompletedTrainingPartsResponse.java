package com.hutech.easylearning.dto.reponse;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompletedTrainingPartsResponse {
    int totalTrainingParts;
    int totalCompletedTrainingParts;
}