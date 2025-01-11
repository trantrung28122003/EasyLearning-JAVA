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
import com.hutech.easylearning.service.*;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ShoppingCartResponse getShoppingCart() {
        return shoppingCartService.getShoppingCartByCurrentUser();
    }

    @PostMapping("/addToCart")
    public ApiResponse<ShoppingCartItem> createShoppingCartItem(@RequestParam  String courseId) {
        try {
            boolean isCourseFull = courseService.isCourseOfflineFull(courseId);
            boolean isRegistrationDateExpired = courseService.isRegistrationDateExpired(courseId);
            if (isCourseFull && isRegistrationDateExpired) {
                return ApiResponse.<ShoppingCartItem>builder()
                        .code(400)
                        .result(null)
                        .message("Khóa học đã đầy và ngày đăng ký đã hết hạn")
                        .build();
            } else if (isRegistrationDateExpired) {
                return ApiResponse.<ShoppingCartItem>builder()
                        .code(401)
                        .result(null)
                        .message("Ngày đăng ký đã hết hạn")
                        .build();
            } else if (isCourseFull) {
                return ApiResponse.<ShoppingCartItem>builder()
                        .code(402)
                        .result(null)
                        .message("Khóa học đã đầy số lượng đăng ký")

                        .build();
            } else {
                return ApiResponse.<ShoppingCartItem>builder()
                        .code(200)
                        .result(shoppingCartItemService.createShoppingCartItem(courseId))
                        .build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ApiResponse.<ShoppingCartItem>builder()
                    .code(500)
                    .result(null)
                    .message("Đã xảy ra lỗi trong quá trình xóa ghi chú")
                    .build();
        }
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
                    .message(e.getMessage())
                    .build();
        }
    }

}
