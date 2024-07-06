package com.hutech.easylearning.dto.reponse;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackInfoResponse {
    String feedbackId;
    String courseId;
    String userId;
    String avatar;
    String fullNameUser;
    String typeUser;
    String content;
    LocalDateTime dateChange;
}
