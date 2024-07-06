package com.hutech.easylearning.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class PaymentService {

    public String doPayment(String amount, String orderInfo) {
        try {
        String endpoint = "https://test-payment.momo.vn/gw_payment/transactionProcessor";
        String partnerCode = "MOMOOJOI20210710";
        String accessKey = "iPXneGmrJH0G8FOP";
        String secretKey = "sFcbSGRSJjwGxwhhcEktCHWYUuTuPNDB";
        String returnUrl = "http://localhost:8080/payment/confirmPaymentMomoClient";
<<<<<<< HEAD
        String notifyUrl = "https://a0be-171-240-253-237.ngrok-free.app/payment/confirmPaymentMomoClient";
=======
        String notifyUrl = "https://4c8d-2001-ee0-5045-50-58c1-b2ec-3123-740d.ap.ngrok.io/Home/SavePayment";
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369
        String orderId = String.valueOf(System.currentTimeMillis());
        String requestId = String.valueOf(System.currentTimeMillis());
        String extraData = "";
        String encodedOrderInfo = URLEncoder.encode(orderInfo, StandardCharsets.UTF_8.toString());
        String rawHash = "partnerCode=" + partnerCode +
                "&accessKey=" + accessKey +
                "&requestId=" + requestId +
                "&amount=" + amount +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&returnUrl=" + returnUrl +
                "&notifyUrl=" + notifyUrl +
                "&extraData=" + extraData;

<<<<<<< HEAD
        System.out.println("Raw hash: " + rawHash);

        String signature = signSHA256(rawHash, secretKey);
        System.out.println("Signature: " + signature);
=======
        System.out.println("Raw hash: " + rawHash); // Debugging
        String signature = signSHA256(rawHash, secretKey);
        System.out.println("Signature: " + signature); // Debugging
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369

        JSONObject message = new JSONObject();
        message.put("partnerCode", partnerCode);
        message.put("accessKey", accessKey);
        message.put("requestId", requestId);
        message.put("amount", amount);
        message.put("orderId", orderId);
        message.put("orderInfo", orderInfo);
        message.put("returnUrl", returnUrl);
        message.put("notifyUrl", notifyUrl);
        message.put("extraData", extraData);
        message.put("requestType", "captureMoMoWallet");
        message.put("signature", signature);

        RestTemplate restTemplate = new RestTemplate();
        String responseFromMomo = restTemplate.postForObject(endpoint, message.toString(), String.class);

        System.out.println(responseFromMomo); // Debugging

        JSONObject jmessage = new JSONObject(responseFromMomo);
        if (jmessage.has("payUrl")) {
            return jmessage.getString("payUrl");
        } else {
            System.out.println("Không tìm thấy khóa 'payUrl' trong phản hồi từ Momo.");
            return null;
        }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String signSHA256(String message, String key) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8));

            // Convert hash to hex string to avoid Base64 encoding issues
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
