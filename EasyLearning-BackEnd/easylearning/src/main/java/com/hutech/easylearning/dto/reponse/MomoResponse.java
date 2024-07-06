package com.hutech.easylearning.dto.reponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MomoResponse {
     String partnerCode;
     String partnerRefId;
     String amount;
     String momoTransId;
     String message;
     String localMessage;
     String responseTime;
     String errorCode;
     String payUrl;
     String signature;
}
