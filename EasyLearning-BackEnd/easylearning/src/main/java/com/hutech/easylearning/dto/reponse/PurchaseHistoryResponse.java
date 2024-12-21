package com.hutech.easylearning.dto.reponse;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseHistoryResponse {
    BigDecimal initialAmount;
    BigDecimal discountedAmount;
    LocalDateTime dateOfFirstPurchase;
    LocalDateTime dateOfLatestPurchase;
    List<OrderDetailResponse> orderDetailResponseList;
}
