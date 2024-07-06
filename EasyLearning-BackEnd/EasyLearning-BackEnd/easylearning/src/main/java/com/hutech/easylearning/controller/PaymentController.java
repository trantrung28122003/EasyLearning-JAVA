package com.hutech.easylearning.controller;



import com.hutech.easylearning.dto.request.MomoRequest;
import com.hutech.easylearning.dto.request.OrderRequest;
import com.hutech.easylearning.dto.request.TrainingPartCreationRequest;
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

    @GetMapping("/confirmPaymentMomoClient")
    public String confirmPaymentMomoClient(
            @RequestParam String partnerCode,
            @RequestParam String accessKey,
            @RequestParam String requestId,
            @RequestParam String amount,
            @RequestParam String orderId,
            @RequestParam String orderInfo,
            @RequestParam String orderType,
            @RequestParam String transId,
            @RequestParam String message,
            @RequestParam String localMessage,
            @RequestParam String responseTime,
            @RequestParam String errorCode,
            @RequestParam String payType,
            @RequestParam String extraData,
            @RequestParam String signature) {

        System.out.println("partnerCode: " + partnerCode);
        System.out.println("accessKey: " + accessKey);
        System.out.println("requestId: " + requestId);
        System.out.println("amount: " + amount);
        System.out.println("orderId: " + orderId);
        System.out.println("orderInfo: " + orderInfo);
        System.out.println("orderType: " + orderType);
        System.out.println("transId: " + transId);
        System.out.println("message: " + message);
        System.out.println("localMessage: " + localMessage);
        System.out.println("responseTime: " + responseTime);
        System.out.println("errorCode: " + errorCode);
        System.out.println("payType: " + payType);
        System.out.println("extraData: " + extraData);
        System.out.println("signature: " + signature);

        if ("0".equals(errorCode)) {
            OrderRequest orderRequest = OrderRequest.builder()
                    .amount(amount)
                    .note("Thanh toán thành công").build();
            orderService.createOrder(orderRequest);
            return "Payment successful";
        } else {
            return "Payment failed: " + message;
        }
    }
}

