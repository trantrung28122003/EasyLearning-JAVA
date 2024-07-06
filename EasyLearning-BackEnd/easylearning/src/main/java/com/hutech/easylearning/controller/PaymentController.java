package com.hutech.easylearning.controller;


<<<<<<< HEAD
=======
import com.hutech.easylearning.dto.reponse.MomoResponse;
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369
import com.hutech.easylearning.dto.request.MomoRequest;
import com.hutech.easylearning.dto.request.OrderRequest;
import com.hutech.easylearning.dto.request.TrainingPartCreationRequest;
import com.hutech.easylearning.service.OrderService;
import com.hutech.easylearning.service.PaymentService;
import jakarta.validation.Valid;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@Slf4j
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @PostMapping("/doPaymentMomo")
    public String doPayment(@RequestBody @Valid MomoRequest request) {
<<<<<<< HEAD
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

=======
        return paymentService.doPayment(request.getAmount(), request.getNote());
    }

    @PostMapping("/confirmPaymentMomoClient")
    public String confirmPaymentMomoClient(@RequestBody MomoResponse result) {

        String rErrorCode = result.getErrorCode();

        System.out.println("ErrorCode: " + rErrorCode);
        System.out.println("message: " + result.getMessage());
        System.out.println("orderInfo: ");
        System.out.println("amount: " + result.getAmount());
        try {
            if ("0".equals(rErrorCode)) {
                OrderRequest orderRequest = OrderRequest.builder()
                        .amount(result.getAmount())
                        .note("aaaa").build();
                orderService.createOrder(orderRequest);
                return "success";
                }
            else
            {
                return "FailurePayment";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "FailurePayment";
        }
    }
}
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369
