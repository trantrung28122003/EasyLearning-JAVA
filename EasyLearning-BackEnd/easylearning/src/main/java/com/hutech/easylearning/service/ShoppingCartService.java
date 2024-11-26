package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.ShoppingCartItemResponse;
import com.hutech.easylearning.dto.reponse.ShoppingCartResponse;
import com.hutech.easylearning.dto.request.ShoppingCartItemRequest;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.ShoppingCart;
import com.hutech.easylearning.entity.ShoppingCartItem;
import com.hutech.easylearning.entity.User;
import com.hutech.easylearning.exception.AppException;
import com.hutech.easylearning.exception.ErrorCode;
import com.hutech.easylearning.repository.CourseRepository;
import com.hutech.easylearning.repository.ShoppingCartItemRepository;
import com.hutech.easylearning.repository.ShoppingCartRepository;
import com.hutech.easylearning.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingCartService {

    ShoppingCartRepository shoppingCartRepository;

    UserRepository userRepository;

    ShoppingCartItemRepository shoppingCartItemRepository;

    DiscountService discountService;
    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart getShoppingCartById(String id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found with id: " + id));
    }

    public BigDecimal calculateTotalPrice(List<ShoppingCartItem> shoppingCartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ShoppingCartItem item : shoppingCartItems) {
            totalPrice = totalPrice.add(item.getCartItemPrice());
        }
        return totalPrice;
    }

    public BigDecimal calculateTotalPriceDiscount(List<ShoppingCartItemResponse> shoppingCartItemResponses) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (var shoppingCartItemResponse : shoppingCartItemResponses) {
            totalPrice = totalPrice.add(shoppingCartItemResponse.getCartItemPriceDiscount());
        }
        return totalPrice;
    }


    public ShoppingCartResponse getShoppingCartByCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        var currentUser = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var shoppingCart = shoppingCartRepository.findShoppingCartByUserId(currentUser.getId());
        List<ShoppingCartItemResponse> shoppingCartItemResponses = new ArrayList<>();
        var shoppingCartItems =  shoppingCartItemRepository.findShoppingCartItemByShoppingCartId(shoppingCart.getId())
                                                            .stream()
                                                            .filter(shoppingCartItem -> !shoppingCartItem.isDeleted())
                                                            .collect(Collectors.toList());


        for(var shoppingCartItem : shoppingCartItems)
        {
            Course courseById = courseRepository.findById(shoppingCartItem.getCourseId()).orElseThrow();
            var coursePriceDiscount = discountService.applyCourseDiscount(courseById.getId(), courseById.getCoursePrice());



            ShoppingCartItemResponse shoppingCartItemResponse = new ShoppingCartItemResponse().builder()
                    .id(shoppingCartItem.getId())
                    .cartItemName(shoppingCartItem.getCartItemName())
                    .cartItemPrice(shoppingCartItem.getCartItemPrice())
                    .cartItemPriceDiscount(coursePriceDiscount)
                    .quantity(shoppingCartItem.getQuantity())
                    .imageUrl(shoppingCartItem.getImageUrl())
                    .courseId(shoppingCartItem.getCourseId())
                    .build();
            shoppingCartItemResponses.add(shoppingCartItemResponse);
            shoppingCartItem.setCartItemPriceDiscount(shoppingCartItemResponse.getCartItemPriceDiscount());
            shoppingCartItemRepository.save(shoppingCartItem);
        }


        BigDecimal totalPrice = calculateTotalPrice(shoppingCartItems);
        BigDecimal totalPriceDiscount = calculateTotalPriceDiscount(shoppingCartItemResponses);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalPriceDiscount(totalPriceDiscount);
        shoppingCartRepository.save(shoppingCart);

        ShoppingCartResponse shoppingCartResponse = ShoppingCartResponse.builder()
                .id(shoppingCart.getId())
                .totalPrice(shoppingCart.getTotalPrice())
                .totalPriceDiscount(totalPriceDiscount)
                .userId(shoppingCart.getUserId())
                .shoppingCartItemResponses(shoppingCartItemResponses)
                .dateCreate(shoppingCart.getDateCreate())
                .dateChange(shoppingCart.getDateChange())
                .changedBy(shoppingCart.getChangedBy())
                .isDeleted(shoppingCart.isDeleted())
                .build();

        return shoppingCartResponse;
    }

    @Transactional
    public ShoppingCart createShoppingCartByUser(String userId) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(userId)
                .isDeleted(false)
                .build();
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public void deleteShoppingCart(String id) {
        shoppingCartRepository.deleteById(id);
    }


    @Transactional
    public void softDeleteShoppingCart(String id) {
        ShoppingCart shoppingCart = getShoppingCartById(id);
        shoppingCart.setDeleted(true);
        shoppingCartRepository.save(shoppingCart);
    }

    public boolean isCourseInCart(String courseId) {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        var currentUser = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var shoppingCart = shoppingCartRepository.findShoppingCartByUserId(currentUser.getId());
        if (shoppingCart == null) {
            return false;
        }

        var shoppingCartItems =  shoppingCartItemRepository.findShoppingCartItemByShoppingCartId(shoppingCart.getId())
                .stream()
                .filter(shoppingCartItem -> !shoppingCartItem.isDeleted())
                .collect(Collectors.toList());

        for (var shoppingCartItem : shoppingCartItems) {
            if (shoppingCartItem.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }

}

