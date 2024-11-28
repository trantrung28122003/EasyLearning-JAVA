package com.hutech.easylearning.controller;

import com.hutech.easylearning.dto.reponse.UserCertificateResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.CertificateRequest;
import com.hutech.easylearning.dto.request.CourseEventCreationRequest;
import com.hutech.easylearning.entity.Certificate;
import com.hutech.easylearning.entity.CourseEvent;
import com.hutech.easylearning.repository.CertificateRepository;
import com.hutech.easylearning.service.CertificateService;
import com.hutech.easylearning.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {

    @Autowired
    private CertificateService certificateService;
    @Autowired
    private NotificationService notificationService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/createCertificate")
    public ApiResponse<Certificate> createCertificate(@RequestParam String courseId) {

        var notificationResponse = notificationService.addNotificationByCertification(courseId);
        simpMessagingTemplate.convertAndSend("/topic/notifications", notificationResponse);
        return ApiResponse.<Certificate>builder()
                .result(certificateService.creatCertificate(courseId))
                .build();
    }

    @GetMapping("/getAllCertificate")
    public ApiResponse<List<UserCertificateResponse>> getAllCertificatesByUser() {
        return ApiResponse.<List<UserCertificateResponse>>builder()
                .result(certificateService.getAllCertificatesByUser())
                .build();
    }
    @GetMapping("/getCertificateByCourseAndUser")
    public ApiResponse<UserCertificateResponse> getCertificatesByCourseAndUser(@RequestParam String courseId) {
        return ApiResponse.<UserCertificateResponse>builder()
                .result(certificateService.getCertificatesByCourseAndUser(courseId))
                .build();
    }
}
