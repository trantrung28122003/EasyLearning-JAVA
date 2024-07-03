package com.hutech.easylearning.dto.request;

import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.ShoppingCart;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartItemRequest {

    String cartItemName;

    String imageUrl;

    BigDecimal cartItemPrice;

    LocalDateTime dateCreate;

    LocalDateTime dateChange;

    String changedBy;

    boolean isDeleted;
}
