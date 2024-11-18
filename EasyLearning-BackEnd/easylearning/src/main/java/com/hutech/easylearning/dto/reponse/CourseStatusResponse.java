package com.hutech.easylearning.dto.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseStatusResponse {

    @JsonProperty("isPurchased")
    private boolean isPurchased;

    @JsonProperty("isLearning")
    private boolean isLearning;

    @JsonProperty("isInCart")
    private boolean isInCart;
}

