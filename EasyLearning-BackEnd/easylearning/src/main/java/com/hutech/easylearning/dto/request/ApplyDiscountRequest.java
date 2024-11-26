package com.hutech.easylearning.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplyDiscountRequest {
    String shoppingCartId;
    String discountCode;
}
