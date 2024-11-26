package com.hutech.easylearning.dto.reponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartItemResponse {
    String id;
    String cartItemName;

    Integer quantity;

    String imageUrl;

    BigDecimal cartItemPrice;

    BigDecimal cartItemPriceDiscount;

    String courseId;

}
