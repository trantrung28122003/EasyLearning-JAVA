package com.hutech.easylearning.dto.reponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hutech.easylearning.entity.Answer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExerciseResponse {
    String id;
    @JsonProperty("questionText")
    String questionText;
    @JsonProperty("answers")
    List<AnswerResponse> answerResponses;
}
