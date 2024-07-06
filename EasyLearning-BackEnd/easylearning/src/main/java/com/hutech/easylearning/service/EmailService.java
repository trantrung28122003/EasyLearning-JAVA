package com.hutech.easylearning.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.fromEmail}")
    private String fromEmail;

    public void sendVerificationCode(String toEmail, String subject, String verificationCode) throws MessagingException, IOException {
        // Đọc template email
        String emailTemplatePath = Paths.get("easylearning/src/main/resources/templates/Email/EmailVerification.html").toString();
        String emailTemplate = new String(Files.readAllBytes(Paths.get(emailTemplatePath)));

        String emailBody = emailTemplate.replace("[verificationCode]", verificationCode);

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", String.valueOf(port));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Tạo session với chứng thực
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Tạo tin nhắn email
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject(subject);
        msg.setContent(emailBody, "text/html; charset=utf-8");

        // Gửi email
        Transport.send(msg);
        System.out.println("Email sent successfully.");
    }

    public void sendEmailPaymentAsync(String toEmail, String subject, String customerName, String totalAmount, String totalCourses, String authorizationCode, String orderDate, List<String> courseNameList) {
        try {

            String emailTemplatePath = Paths.get("easylearning/src/main/resources/templates/Email/EmailPayment.html").toString();
            String emailTemplate = new String(Files.readAllBytes(Paths.get(emailTemplatePath)));


            StringBuilder coursesBuilder = new StringBuilder();

            for (String courseName : courseNameList) {
                coursesBuilder.append("<li>").append(courseName).append("</li>");
            }

            String courses = coursesBuilder.toString();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.000");
            decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols() {{
                setDecimalSeparator(',');
            }});
            String formattedTotalAmount = decimalFormat.format(Double.parseDouble(totalAmount));

            String emailBody = emailTemplate
                    .replace("[CustomerName]", customerName != null ? customerName : "")
                    .replace("[totalAmount]", formattedTotalAmount != null ? formattedTotalAmount : "")
                    .replace("[totalCourses]", totalCourses != null ? totalCourses : "")
                    .replace("[listCourses]", courses)
                    .replace("[orderDate]", orderDate != null ? orderDate : "")
                    .replace("[authorizationCode]", authorizationCode != null ? authorizationCode : "");


            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", String.valueOf(port));
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");


            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject(subject);
            msg.setContent(emailBody, "text/html; charset=utf-8");

            Transport.send(msg);
            System.out.println("Email sent successfully.");
        } catch (MessagingException | IOException ex) {
            System.out.println("Error sending email: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
