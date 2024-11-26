package com.hutech.easylearning.controller;



import com.hutech.easylearning.dto.request.*;
import com.hutech.easylearning.service.OrderService;
import com.hutech.easylearning.service.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    OrderService orderService;



    @PostMapping("/doPaymentMomo")
    public String doPayment(@RequestBody @Valid MomoRequest request) {
        request.setNote("Thanh toan khoa hoc");
        return paymentService.doPayment(request.getAmount(), request.getNote());
    }

    @PostMapping("/confirmPaymentMomoClient")
    public ApiResponse<String> confirmPaymentMomoClient(@RequestBody @Valid ConfirmPaymentSuccessRequest request) {
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
                    .note("Thanh toán thành công")
                    .build();
            orderService.processPaymentAndCreateOrder(orderRequest);

            return ApiResponse.<String>builder()
                    .result("Payment successful")
                    .build();
        } else {
//            OrderRequest orderRequest = OrderRequest.builder()
//                    .amount(request.getAmount())
//                    .note("Thanh toán thành công")
//                    .build();
//            orderService.processPaymentAndCreateOrder(orderRequest);

            return ApiResponse.<String>builder()
                    .result("Payment failed: " + request.getMessage())
                    .build();
        }
    }

}

