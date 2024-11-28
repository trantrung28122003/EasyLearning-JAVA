package com.hutech.easylearning.dto.reponse;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {
    String contentNotification;
    LocalDateTime dateCreate;
    Boolean isRead;
}
