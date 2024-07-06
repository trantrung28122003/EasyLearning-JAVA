package com.hutech.easylearning.dto.reponse;

import com.hutech.easylearning.entity.ShoppingCartItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartResponse {
    String id;
    BigDecimal totalPrice;
    List<ShoppingCartItem> shoppingCartItems;
    String userId;
    LocalDateTime dateCreate;
    LocalDateTime dateChange;
    String changedBy;
    boolean isDeleted;
}
