package com.hutech.easylearning.service;


import com.hutech.easylearning.entity.Discount;
import com.hutech.easylearning.entity.UserDiscount;
import com.hutech.easylearning.repository.DiscountRepository;
import com.hutech.easylearning.repository.UserDiscountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDiscountService {
    final UserDiscountRepository userDiscountRepository;
    private final DiscountRepository discountRepository;

    public UserDiscount addUserDiscount(String userId, String discountCode) {
        Discount discount = discountRepository.findByDiscountCode(discountCode).orElseThrow();
        UserDiscount userDiscount = UserDiscount.builder()
                .id(null)
                .userId(userId)
                .discountId(discount.getId())
                .active(true)
                .isUsed(false)
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(userId)
                .isDeleted(false)
                .build();
        return userDiscountRepository.save(userDiscount);
    }
}
