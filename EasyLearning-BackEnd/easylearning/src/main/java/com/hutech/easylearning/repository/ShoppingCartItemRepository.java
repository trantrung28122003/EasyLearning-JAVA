package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseDetail;
import com.hutech.easylearning.entity.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, String> {
    List<ShoppingCartItem> findShoppingCartItemByShoppingCartId (String shoppingCartId);
    List<ShoppingCartItem> findShoppingCartItemByCourseId(String courseId);
}
