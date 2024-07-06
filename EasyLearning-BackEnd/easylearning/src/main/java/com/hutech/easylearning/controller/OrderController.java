package com.hutech.easylearning.controller;

import com.hutech.easylearning.dto.request.ApiResponse;
import com.hutech.easylearning.dto.request.CourseCreationRequest;
import com.hutech.easylearning.dto.request.CourseUpdateRequest;
import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.Order;
import com.hutech.easylearning.service.CourseService;
import com.hutech.easylearning.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return orders.stream()
                .filter(order -> !order.isDeleted())
                .collect(Collectors.toList());
    }
}
