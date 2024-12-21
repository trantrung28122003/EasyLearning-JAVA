package com.hutech.easylearning.controller;




import com.hutech.easylearning.dto.request.*;
import com.hutech.easylearning.service.*;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    OrderService orderService;
    @Autowired
    private NotificationService notificationService;


    @PostMapping("/doPaymentMomo")
    public String doPayment(@RequestBody @Valid MomoRequest request) {
        request.setNote("Thanh toan khoa hoc");
        return paymentService.doPaymentMOMO(request.getAmount(), request.getNote());
    }

    @PostMapping("/confirmPaymentMomoClient")
    public ApiResponse<String> confirmPaymentMomoClient(@RequestBody @Valid ConfirmPaymentMomoRequest request) {
        System.out.println("partnerCode: " + request.getPartnerCode());
        System.out.println("accessKey: " + request.getAccessKey());
        System.out.println("requestId: " + request.getRequestId());
        System.out.println("amount: " + request.getAmount());
        System.out.println("orderId: " + request.getOrderId());
        System.out.println("orderInfo: " + request.getOrderInfo());
        System.out.println("orderType: " + request.getOrderType());
        System.out.println("transId: " + request.getTransId());
        System.out.println("message: " + request.getMessage());
        System.out.println("localMessage: " + request.getLocalMessage());
        System.out.println("responseTime: " + request.getResponseTime());
        System.out.println("errorCode: " + request.getErrorCode());
        System.out.println("payType: " + request.getPayType());
        System.out.println("extraData: " + request.getExtraData());
        System.out.println("signature: " + request.getSignature());

        if ("0".equals(request.getErrorCode())) {
            OrderRequest orderRequest = OrderRequest.builder()
                    .amount(request.getAmount())
                    .paymentMethod("MOMO")
                    .note("Thanh toán thành công")
                    .build();
            orderService.processPaymentAndCreateOrder(orderRequest);
            return ApiResponse.<String>builder()
                    .code(200)
                    .result("Payment successful")
                    .build();
        } else {
            return ApiResponse.<String>builder()
                    .code(400)
                    .result("Payment failed: " + request.getMessage())
                    .build();
        }
    }


    @GetMapping("/doPaymentVNPay")
    public String doPaymentVNPay(@RequestParam long amount) throws UnsupportedEncodingException {
        return paymentService.doPaymentVNPAY(amount);
    }

    @PostMapping("/confirmPaymentVNPayClient")
    public ApiResponse<String> confirmPaymentVNPayClient(@RequestBody @Valid ConfirmPaymentVNPayRequest request) {
        if ("00".equals(request.getErrorCode())) {
            long amount = Long.parseLong(request.getAmount()) / 100;
            String amountString = String.valueOf(amount);
            OrderRequest orderRequest = OrderRequest.builder()
                    .amount(amountString)
                    .note("Thanh toán thành công")
                    .paymentMethod("VNPAY")
                    .build();

            orderService.processPaymentAndCreateOrder(orderRequest);
            return ApiResponse.<String>builder()
                    .code(200)
                    .result("Payment successful")
                    .build();
        } else {
            return ApiResponse.<String>builder()
                    .code(400)
                    .result("Payment failed: " + request.getMessage())
                    .build();
        }
    }

}




