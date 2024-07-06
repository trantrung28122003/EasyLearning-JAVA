package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.SendVerificationCodeRequest;
import com.hutech.easylearning.entity.Category;
import com.hutech.easylearning.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendVerificationCode")
    public ApiResponse<String> sendVerificationCode(@RequestBody SendVerificationCodeRequest request) {
        try {
            String email = request.getEmail();
            String verificationCode = request.getVerificationCode();

            String subject = verificationCode + " - Xác nhận yêu cầu đặt lại mật khẩu tài khoản của bạn";
            emailService.sendVerificationCode(email, subject, verificationCode);

            return ApiResponse.<String>builder()
                    .result("Email sent successfully")
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ApiResponse.<String>builder()
                    .code(500)
                    .message("Failed to send email")
                    .build();
        }
    }
}
