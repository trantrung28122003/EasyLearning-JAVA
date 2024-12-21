package com.hutech.easylearning.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmPaymentVNPayRequest {
    String partnerCode;
    String accessKey ;
    String requestId ;
    String amount;
    String orderId;
    String orderInfo ;
    String orderType ;
    String transId;
    String message ;
    String localMessage ;
    String responseTime ;
    String errorCode ;
    String payType ;
    String extraData;
    String signature ;
}
