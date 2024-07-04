package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.MomoResponse;
import com.hutech.easylearning.dto.request.MomoRequest;
import com.hutech.easylearning.dto.request.OrderRequest;
import com.hutech.easylearning.dto.request.TrainingPartCreationRequest;
import com.hutech.easylearning.service.OrderService;
import com.hutech.easylearning.service.PaymentService;
import jakarta.validation.Valid;
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
