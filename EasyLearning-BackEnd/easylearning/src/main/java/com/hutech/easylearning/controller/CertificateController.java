package com.hutech.easylearning.controller;

import com.hutech.easylearning.dto.reponse.UserCertificateResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.CertificateRequest;
import com.hutech.easylearning.dto.request.CourseEventCreationRequest;
import com.hutech.easylearning.entity.Certificate;
import com.hutech.easylearning.entity.CourseEvent;
import com.hutech.easylearning.repository.CertificateRepository;
import com.hutech.easylearning.service.CertificateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;


//    @PostMapping("/download")
//    public ResponseEntity<byte[]> downloadCertificate(@RequestBody CertificateRequest request) {
//        byte[] pdfData = certificateService.generateCertificate(
//                request.getUserName(),
//                request.getCourseName(),
//                request.getIssueDate(),
//                request.getCertificateNumber()
//        );
//        if (pdfData == null || pdfData.length == 0) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error: Unable to generate certificate.".getBytes());
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/pdf");
//        headers.add("Content-Disposition", "attachment; filename=certificate.pdf");
//        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
//    }

    @PostMapping("/createCertificate")
    public ApiResponse<Certificate> createCertificate(@RequestParam String courseId) {
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
