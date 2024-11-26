package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.CourseDiscount;
import com.hutech.easylearning.entity.Discount;
import com.hutech.easylearning.entity.UserDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDiscountRepository extends JpaRepository<UserDiscount, String> {
    List<UserDiscount> findAllByUserIdAndIsUsedFalse(String userId);
    Optional<UserDiscount> findByUserIdAndDiscountIdAndIsUsedTrue(String userId, String discountId);
    Optional<UserDiscount> findByDiscountIdAndUserId(String discountId, String userId);

}
