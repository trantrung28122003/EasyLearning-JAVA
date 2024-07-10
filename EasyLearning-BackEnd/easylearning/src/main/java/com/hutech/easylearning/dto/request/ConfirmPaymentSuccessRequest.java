package com.hutech.easylearning.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmPaymentSuccessRequest {
    private String partnerCode;
    private String accessKey;
    private String requestId;
    private String amount;
    private String orderId;
    private String orderInfo;
    private String orderType;
    private String transId;
    private String message;
    private String localMessage;
    private String responseTime;
    private String errorCode;
    private String payType;
    private String extraData;
    private String signature;
}
