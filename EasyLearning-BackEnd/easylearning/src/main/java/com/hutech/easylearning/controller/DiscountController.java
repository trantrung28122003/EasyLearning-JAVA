package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.UserDiscountResponse;
import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.entity.Certificate;
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

    @PostMapping("/addUserDiscount")
    public ApiResponse<UserDiscount> createUserDiscount(@RequestParam String discountCode) {
        return ApiResponse.<UserDiscount>builder()
                .result(discountService.addUserDiscount(discountCode))
                .build();
    }

    @GetMapping("/getAllDisCountByUser")
    public ApiResponse<List<UserDiscountResponse>> getAllDiscount() {
        return ApiResponse.<List<UserDiscountResponse>>builder()
                .result(discountService.getAllDisCountByUser())
                .build();
    }
}
