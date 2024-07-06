package com.hutech.easylearning.dto.request;

import com.hutech.easylearning.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserCreationRequest {
    private String userName;

    private String email;

    private String fullName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate dayOfBirth;

    private String imageUrl;

    @Size(min = 8, message ="pass word must be at least 8 characters")
    private String password;
}
