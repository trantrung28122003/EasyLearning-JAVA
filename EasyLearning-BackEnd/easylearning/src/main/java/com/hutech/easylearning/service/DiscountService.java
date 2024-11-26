package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.ApplyDiscountResponse;
import com.hutech.easylearning.dto.reponse.UserCertificateResponse;
import com.hutech.easylearning.dto.reponse.UserDiscountResponse;
import com.hutech.easylearning.dto.request.ApplyDiscountRequest;
import com.hutech.easylearning.entity.CourseDiscount;
import com.hutech.easylearning.entity.Discount;
import com.hutech.easylearning.entity.UserDiscount;
import com.hutech.easylearning.enums.DiscountType;
import com.hutech.easylearning.repository.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private  CourseDiscountRepository courseDiscountRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseDetailRepository courseDetailRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserDiscountRepository userDiscountRepository;
    @Autowired
    private UserService userService;


    public Discount createDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public Optional<Discount> getDiscountByCode(String discountCode) {
        return discountRepository.findByDiscountCode(discountCode);
    }


    public BigDecimal applyCourseDiscount(String courseId, BigDecimal originalPrice) {
        var course = courseRepository.findById(courseId).orElseThrow();
        List<CourseDiscount> courseDiscounts = courseDiscountRepository.findAllByCourseId(courseId);
        BigDecimal discountPercent = new BigDecimal("0");
        BigDecimal discountedPrice = originalPrice;

        for (CourseDiscount courseDiscount : courseDiscounts) {
            if (courseDiscount.getActive() && !courseDiscount.getIsCodeRequired()) {
                Discount discountByCourseId = discountRepository.findById(courseDiscount.getDiscountId()).orElseThrow();

                if (discountByCourseId.getDiscountType() == DiscountType.PERCENT) {
                    discountPercent = originalPrice.multiply(discountByCourseId.getValue()).divide(new BigDecimal("100"));
                    discountedPrice = discountedPrice.subtract(discountPercent);
                }

                if(discountByCourseId.getDiscountType() == DiscountType.FIXED) {
                    discountedPrice = discountedPrice.subtract(discountByCourseId.getValue());
                }
            }
        }
        discountedPrice = discountedPrice.setScale(0, RoundingMode.HALF_UP);
        return discountedPrice.max(BigDecimal.ZERO);
    }


    public boolean checkDiscountUsed(String userId, String discountId) {
        Optional<UserDiscount> userDiscount = userDiscountRepository.findByUserIdAndDiscountIdAndIsUsedTrue(userId, discountId);
        return userDiscount.isPresent();
    }


public ApplyDiscountResponse applyDiscount(ApplyDiscountRequest applyDiscountRequest) {

        var shoppingCart = shoppingCartRepository.findById(applyDiscountRequest.getShoppingCartId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giỏ hàng"));
        BigDecimal discountPercent = new BigDecimal("0");
        var originalTotal = shoppingCart.getTotalPriceDiscount();
        var discountCode = applyDiscountRequest.getDiscountCode();
        BigDecimal discountedPrice = originalTotal;

        Discount discount = discountRepository.findByDiscountCode(discountCode)
                .orElseThrow(() -> new IllegalArgumentException("Mã giảm giá không hợp lệ "));

        var currentUser = userService.getMyInfo();

        if (checkDiscountUsed(currentUser.getId(), discount.getId())) {
            throw new IllegalArgumentException("Bạn đã sử dụng mã giảm giá này rồi.");
        }

        if (discount.getEndDate() != null && discount.getEndDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Mã giảm giá đã hết hạn.");
        }

        if (discount.getUsageLimit() != null && discount.getUsageCount() >= discount.getUsageLimit()) {
            throw new IllegalArgumentException("Mã giảm giá đã đạt giới hạn sử dụng.");
        }

        if (discount.getDiscountType() == DiscountType.PERCENT) {
            discountPercent = originalTotal.multiply(discount.getValue()).divide(new BigDecimal("100"));
            discountedPrice = discountedPrice.subtract(discountPercent);
        }

        if(discount.getDiscountType() == DiscountType.FIXED) {
            discountedPrice = discountedPrice.subtract(discount.getValue());
        }

        discountedPrice = discountedPrice.setScale(0, RoundingMode.HALF_UP);
        discountedPrice = discountedPrice.max(BigDecimal.ZERO);
        ApplyDiscountResponse applyDiscountResponse = ApplyDiscountResponse.builder()
                .discountCode(discount.getDiscountCode())
                .discountName(discount.getDiscountName())
                .value(discount.getValue())
                .priceDiscount(discountedPrice)
                .build();
        return applyDiscountResponse;
    }

    public UserDiscount addUserDiscount(String discountCode) {
        var currentUser = userService.getMyInfo();

        Discount discount = discountRepository.findByDiscountCode(discountCode)
                .orElseThrow(() -> new IllegalArgumentException("Mã giảm giá không hợp lệ "));

        Optional<UserDiscount> existingUserDiscount = userDiscountRepository.findByDiscountIdAndUserId(discount.getId(), currentUser.getId());
        if (existingUserDiscount.isPresent()) {
            throw new IllegalArgumentException("Mã giảm giá này đã được sử dụng.");
        }

        UserDiscount userDiscount = new UserDiscount().builder()
                .discountId(discount.getId())
                .userId(currentUser.getId())
                .changedBy(currentUser.getId())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .isUsed(true)
                .active(true)
                .isDeleted(false)
                .build();

        return userDiscountRepository.save(userDiscount);
    }

    public List<UserDiscountResponse> getAllDisCountByUser() {
        var currentUser = userService.getMyInfo();
        List<UserDiscountResponse> userDiscountResponseList = new ArrayList<>();
        List<UserDiscount>  userDiscounts = userDiscountRepository.findAllByUserIdAndIsUsedFalse(currentUser.getId());

        for(var userDiscount : userDiscounts)
        {
            var discount = discountRepository.findById(userDiscount.getDiscountId()).orElseThrow();
            UserDiscountResponse userDiscountResponse = new UserDiscountResponse().builder()
                    .discountCode(discount.getDiscountCode())
                    .discountDescription(discount.getDescription())
                    .build();

            userDiscountResponseList.add(userDiscountResponse);

        }

        return userDiscountResponseList;
    }


}
