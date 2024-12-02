package com.hutech.easylearning.dto.request;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
    private String confirmPassword;
}