package com.hutech.easylearning.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CertificateRequest {
    private String userName;
    private String courseName;
    private String issueDate;
    private String certificateNumber;
}
