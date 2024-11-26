package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.ApplyDiscountResponse;
import com.hutech.easylearning.dto.reponse.ShoppingCartResponse;
import com.hutech.easylearning.dto.reponse.UserResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.ApplyDiscountRequest;
import com.hutech.easylearning.dto.request.ShoppingCartItemRequest;
import com.hutech.easylearning.dto.request.UserCreationRequest;
import com.hutech.easylearning.entity.ShoppingCart;
import com.hutech.easylearning.entity.ShoppingCartItem;
import com.hutech.easylearning.entity.TrainingPart;
import com.hutech.easylearning.service.DiscountService;
import com.hutech.easylearning.service.ShoppingCartItemService;
import com.hutech.easylearning.service.ShoppingCartService;
import com.hutech.easylearning.service.TrainingPartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartItemService shoppingCartItemService;
    @Autowired
    private DiscountService discountService;

    @GetMapping
    public ShoppingCartResponse getShoppingCart() {
        return shoppingCartService.getShoppingCartByCurrentUser();
    }

    @PostMapping("/addToCart/{courseId}")
    public ApiResponse<ShoppingCartItem> createShoppingCartItem(@PathVariable("courseId") String courseId) {

        return ApiResponse.<ShoppingCartItem>builder()
                .result(shoppingCartItemService.createShoppingCartItem(courseId))
                .build();
    }

    @PostMapping("/remove/{shoppingCartItemId}")
    public String removeShoppingCartItem(@PathVariable("shoppingCartItemId") String shoppingCartItemId) {
        shoppingCartItemService.deleteShoppingCartItem(shoppingCartItemId);
        return "ShoppingCartItem has been soft deleted";
    }

    @GetMapping("/isCourseInCart/{courseId}")
    public boolean isCourseInCart(@PathVariable("courseId") String courseId) {
        return shoppingCartService.isCourseInCart(courseId);
    }

    @PostMapping("/applyDiscount")
    public ApiResponse<ApplyDiscountResponse> applyCartDiscount(@RequestBody ApplyDiscountRequest applyDiscountRequest) {
        try {
            ApplyDiscountResponse response = discountService.applyDiscount(applyDiscountRequest);
            return ApiResponse.<ApplyDiscountResponse>builder()
                    .result(response)
                    .build();
        } catch (IllegalArgumentException e) {
            return ApiResponse.<ApplyDiscountResponse>builder()
                    .message(e.getMessage()) // Trả về thông báo lỗi từ exception
                    .build();
        }
    }

}
