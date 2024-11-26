package com.hutech.easylearning.dto.reponse;

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
public class ShoppingCartResponse {
    String id;
    BigDecimal totalPrice;
    BigDecimal totalPriceDiscount;
    List<ShoppingCartItemResponse> shoppingCartItemResponses;
    String userId;
    LocalDateTime dateCreate;
    LocalDateTime dateChange;
    String changedBy;
    boolean isDeleted;
}
