package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.request.ShoppingCartItemRequest;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseDetail;
import com.hutech.easylearning.entity.ShoppingCartItem;
import com.hutech.easylearning.entity.TrainerDetail;
import com.hutech.easylearning.enums.CourseType;
import com.hutech.easylearning.repository.CourseRepository;
import com.hutech.easylearning.repository.ShoppingCartItemRepository;
import com.hutech.easylearning.repository.ShoppingCartRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingCartItemService {

    ShoppingCartItemRepository shoppingCartItemRepository;
    ShoppingCartRepository shoppingCartRepository;

    CourseRepository courseRepository;

    @Autowired
    UserService userService;

    @Transactional(readOnly = true)
    public List<ShoppingCartItem> getAllShoppingCartItems() {
        return shoppingCartItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ShoppingCartItem getShoppingCartItemById(String id) {
        return shoppingCartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingCartItem not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ShoppingCartItem> getShoppingCartItemByShoppingCart(String shoppingCartId) {
        return shoppingCartItemRepository.findShoppingCartItemByShoppingCartId(shoppingCartId);
    }
    @Transactional
    public ShoppingCartItem createShoppingCartItem(String courseId) {

        var courseById = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));;
        var currentUser = userService.getMyInfo();
        var shoppingCart = shoppingCartRepository.findShoppingCartByUserId(currentUser.getId());
        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder()
                .cartItemName(courseById.getCourseName())
                .cartItemPrice(courseById.getCoursePrice())
                .imageUrl(courseById.getImageUrl())
                .shoppingCartId(shoppingCart.getId())
                .courseId(courseId)
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUser.getId())
                .isDeleted(false)
                .build();
        return shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Transactional
    public ShoppingCartItem updateShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        return shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Transactional
    public void deleteShoppingCartItem(String id) {
        shoppingCartItemRepository.deleteById(id);
    }

    @Transactional
    public void softDeleteShoppingCartItem(String id) {
        ShoppingCartItem shoppingCartItem = getShoppingCartItemById(id);
        shoppingCartItem.setDeleted(true);
        shoppingCartItem.setDateChange(LocalDateTime.now());
        shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Transactional
    public List<ShoppingCartItem> getShoppingCartItemsByCurrentUser() {
        var currentUser = userService.getMyInfo();
        var shoppingCart = shoppingCartRepository.findShoppingCartByUserId(currentUser.getId());
        return shoppingCartItemRepository.findShoppingCartItemByShoppingCartId(shoppingCart.getId());
    }

    @Transactional
    public void softDeleteShoppingCartItemByCourseId(String courseId) {
        var getShoppingCartItemCourseId = shoppingCartItemRepository.findShoppingCartItemByCourseId(courseId);
        for(var shoppingCartItem : getShoppingCartItemCourseId)
        {
            shoppingCartItem.setDeleted(true);
            shoppingCartItem.setDateChange(LocalDateTime.now());
            shoppingCartItemRepository.save(shoppingCartItem);
        }
    }

    @Transactional
    public void restoreShoppingCartItemByCourseId(String courseId) {
        var getShoppingCartItemCourseId = shoppingCartItemRepository.findShoppingCartItemByCourseId(courseId);
        for(var shoppingCartItem : getShoppingCartItemCourseId)
        {
            shoppingCartItem.setDeleted(false);
            shoppingCartItem.setDateChange(LocalDateTime.now());
            shoppingCartItemRepository.save(shoppingCartItem);
        }
    }

}

