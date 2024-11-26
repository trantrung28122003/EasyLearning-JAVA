package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.reponse.UserCertificateResponse;
import com.hutech.easylearning.entity.Certificate;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.repository.CertificateRepository;
import com.hutech.easylearning.repository.CourseRepository;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.io.font.constants.StandardFonts;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CertificateService {

    @Autowired
    private UploaderService uploaderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private CourseRepository courseRepository;

    public String generateCertificate(String userName, String courseName, String issueDate, String certificateNumber) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);

            float pageWidth = 842f;
            float pageHeight = 595f;
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, new PageSize(pageWidth, pageHeight));
            document.setMargins(0, 0, 0, 0);

            String borderImagePath = "src/main/resources/CertificateFile/khung.png";
            ImageData imageData = ImageDataFactory.create(borderImagePath);
            Image borderImage = new Image(imageData)
                    .scaleToFit(pageWidth, pageHeight)
                    .setFixedPosition(0, 0);
            document.add(borderImage);

            PdfFont fontSans;
            PdfFont fontCalligraphy;
            PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
            try (InputStream fontStreamSans = getClass().getClassLoader().getResourceAsStream("CertificateFile/Roboto-Regular.ttf");
                 InputStream fontStreamCalligraphy = getClass().getClassLoader().getResourceAsStream("CertificateFile/GreatVibes-Regular.ttf")) {

                byte[] fontBytesSans = fontStreamSans.readAllBytes();
                byte[] fontBytesCalligraphy = fontStreamCalligraphy.readAllBytes();

                FontProgram fontProgramSans = FontProgramFactory.createFont(fontBytesSans);
                FontProgram fontProgramCalligraphy = FontProgramFactory.createFont(fontBytesCalligraphy);

                fontSans = PdfFontFactory.createFont(fontProgramSans, PdfEncodings.IDENTITY_H);
                fontCalligraphy = PdfFontFactory.createFont(fontProgramCalligraphy, PdfEncodings.IDENTITY_H);
            }

            document.add(new Paragraph("E-LEARNING")
                    .setFont(fontSans)
                    .setFontSize(20)
                    .setBold()
                    .setItalic()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedPosition(0, pageHeight - 90, pageWidth));

            document.add(new Paragraph("CERTIFICATE OF COMPLETETION")
                    .setFont(fontSans)
                    .setFontSize(34)
                    .setFontColor(ColorConstants.ORANGE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedPosition(0, pageHeight - 150, pageWidth));

            document.add(new Paragraph("Chứng nhận hoàn thành ")
                    .setFont(fontSans)
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedPosition(0, pageHeight - 170, pageWidth));

            canvas.setLineWidth(0.5f)
                    .setStrokeColor(ColorConstants.LIGHT_GRAY)
                    .moveTo(300, pageHeight - 168)
                    .lineTo(pageWidth - 300, pageHeight - 168)
                    .stroke();

            document.add(new Paragraph(userName)
                    .setFont(fontCalligraphy)
                    .setFontSize(60)
                    .setBold()
                    .setFontColor(new DeviceRgb(234, 85, 36))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setCharacterSpacing(10f)
                    .setFixedPosition(0, pageHeight - 300, pageWidth));

            document.add(new Paragraph("Đã tham gia và hoàn thành khóa học")
                    .setFont(fontSans)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedPosition(0, pageHeight - 320, pageWidth));

            document.add(new Paragraph(courseName)
                    .setFont(fontSans)
                    .setFontSize(28)
                    .setBold()
                    .setFontColor(ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedPosition(0, pageHeight - 377, pageWidth));

            canvas.setLineWidth(2f)
                    .setStrokeColor(ColorConstants.LIGHT_GRAY)
                    .moveTo(200, pageHeight - 375)
                    .lineTo(pageWidth - 200, pageHeight - 375)
                    .stroke();


            document.add(new Paragraph("Hồ Chí Minh: " + issueDate)
                    .setFont(fontSans)
                    .setFontSize(15)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedPosition(0, 160, pageWidth));

            document.add(new Paragraph("Mã chứng chỉ")
                    .setFont(fontSans)
                    .setFontColor(new DeviceRgb(169, 169, 169))
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(140, 120, pageWidth / 2));

            document.add(new Paragraph(certificateNumber)
                    .setFont(fontSans)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFixedPosition(145, 95, pageWidth / 2));

            document.add(new Paragraph("e-Learning Team")
                    .setFont(fontSans)
                    .setFontSize(16)
                    .setFontColor(new DeviceRgb(169, 169, 169))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFixedPosition(pageWidth / 2, 120, pageWidth / 2 - 120));


            document.add(new Paragraph("e-LEARNING")
                    .setFont(fontSans)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFixedPosition(pageWidth / 2, 95, pageWidth / 2 - 120));


            String ornamentImagePath = "src/main/resources/CertificateFile/logo_certificate.png";
            ImageData ornamentData = ImageDataFactory.create(ornamentImagePath);
            Image ornamentImage = new Image(ornamentData)
                    .scaleToFit(150, 150)
                    .setFixedPosition(pageWidth / 2 - 70, 30);

            document.add(ornamentImage);

            document.close();
            return uploaderService.uploadPdfToCloud(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Certificate creatCertificate(String courseId) {
        var currentUser = userService.getMyInfo();

        Optional<Certificate> existingCertificate = certificateRepository.findByUserIdAndCourseId(currentUser.getId(), courseId);
        if (existingCertificate.isPresent()) {
            throw new IllegalStateException("Chứng chỉ đã được tạo cho người dùng và khóa học này.");
        }
        Certificate certificate = new Certificate();

        var course = courseService.getCourseById(courseId);
        var issueDate = LocalDateTime.now();
        var certificateNumber = generateCertificateNumber();
        String pdfURL = generateCertificate(currentUser.getFullName(), course.getCourseName(), issueDate.toString(), certificateNumber);


        certificate.setUserId(currentUser.getId());
        certificate.setCourseId(courseId);
        certificate.setCertificateNumber(certificateNumber);
        certificate.setCertificateUrl(pdfURL);
        certificate.setDateCreate(issueDate);
        certificate.setIssuedDate(issueDate);
        certificate.setDateChange(issueDate);
        certificate.setExpirationDate(LocalDateTime.now().plusYears(2));

        certificateRepository.save(certificate);
        return  certificate;
    }

    public String generateCertificateNumber() {
        String prefix = "EL";
        String randomNumber = String.format("%06d", new Random().nextInt(1000000));
        return prefix + randomNumber;
    }

    public UserCertificateResponse getCertificatesByCourseAndUser(String courseId)
    {
        var currentUser = userService.getMyInfo();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        Optional<Certificate> certificate = certificateRepository.findByUserIdAndCourseId(currentUser.getId(), courseId);
        UserCertificateResponse userCertificateResponse = new UserCertificateResponse().builder()
                    .issuedDate(certificate.get().getIssuedDate())
                    .certificateNumber(certificate.get().getCertificateNumber())
                    .certificateUrl(certificate.get().getCertificateUrl())
                    .courseName(course.getCourseName())
                    .userFullName(currentUser.getFullName())
                    .expiresDate(certificate.get().getExpirationDate())
                    .build();

        return userCertificateResponse;
    }

    public List<UserCertificateResponse> getAllCertificatesByUser()
    {
        var currentUser = userService.getMyInfo();
        List<Certificate> certificatesByUser = certificateRepository.findAllByUserId(currentUser.getId());
        List<UserCertificateResponse>  userCertificateResponses = new ArrayList<>();
        for(Certificate certificate : certificatesByUser)
        {
            Course course = courseRepository.findById(certificate.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found with id: " + certificate.getCourseId()));
            UserCertificateResponse userCertificateResponse = new UserCertificateResponse().builder()
                    .issuedDate(certificate.getIssuedDate())
                    .certificateNumber(certificate.getCertificateNumber())
                    .certificateUrl(certificate.getCertificateUrl())
                    .courseName(course.getCourseName())
                    .userFullName(currentUser.getFullName())
                    .expiresDate(certificate.getExpirationDate())
                    .build();
            userCertificateResponses.add(userCertificateResponse);
        }
        return userCertificateResponses;
    }
}






