package com.hutech.easylearning.dto.reponse;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCertificateResponse {
    String courseName;
    String userFullName;
    LocalDateTime issuedDate;
    LocalDateTime expiresDate;
    String certificateUrl;
    String certificateNumber;
}
