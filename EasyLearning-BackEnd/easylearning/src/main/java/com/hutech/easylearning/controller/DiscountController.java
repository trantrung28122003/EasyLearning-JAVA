package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.UserDiscountResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.OrderRequest;
import com.hutech.easylearning.entity.UserDiscount;
import com.hutech.easylearning.service.DiscountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/discount")
public class DiscountController {
    private final DiscountService discountService;
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/updateUserDiscount")
    public ApiResponse<String> updateUserDiscount(@RequestParam String discountCode) {
        if (discountService.updateUserDiscount(discountCode)) {
            return ApiResponse.<String>builder()
                    .code(200)
                    .result("Cập nhập mã thành công")
                    .build();
        } else {
            return ApiResponse.<String>builder()
                    .code(400)
                    .result("Cập nhập mã thất bại")
                    .build();
        }
    }

    @GetMapping("/getAllDisCountByUser")
    public ApiResponse<List<UserDiscountResponse>> getAllDiscount() {
        return ApiResponse.<List<UserDiscountResponse>>builder()
                .result(discountService.getAllDisCountByUser())
                .build();
    }
}
