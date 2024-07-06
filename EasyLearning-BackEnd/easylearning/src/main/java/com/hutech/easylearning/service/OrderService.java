package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.request.OrderRequest;
import com.hutech.easylearning.entity.Order;
import com.hutech.easylearning.entity.OrderDetail;
import com.hutech.easylearning.repository.OrderRepository;
<<<<<<< HEAD
import jakarta.validation.constraints.Email;
=======
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<<<<<<< HEAD
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
=======

import java.math.BigDecimal;
import java.time.LocalDateTime;
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;

    @Autowired
    ShoppingCartItemService shoppingCartItemService;

    @Autowired
    UserService userService;

    @Autowired
    OrderDetailService orderDetailService;

<<<<<<< HEAD
    @Autowired
    EmailService emailService;
=======
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369


    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Transactional
    public Order createOrder(OrderRequest request) {
        var shoppingCartItems = shoppingCartItemService.getShoppingCartItemsByCurrentUser();
<<<<<<< HEAD

=======
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369
        BigDecimal orderAmount = new BigDecimal(request.getAmount());
        Order order = Order.builder()
                .orderTotalPrice(orderAmount)
                .orderNotes(request.getNote())
                .orderPaymentMethod("MOMO")
                .orderQuantity(shoppingCartItems.size())
                .dateCreate(LocalDateTime.now())
                .userId(userService.getMyInfo().getId())
                .dateChange(LocalDateTime.now())
                .changedBy(userService.getMyInfo().getId())
                .isDeleted(false)
                .build();
        Order saveOrder = orderRepository.save(order);

        for (var itemShoppingCart : shoppingCartItems) {
            var orderDetail = OrderDetail.builder()
                    .orderId(order.getId())
                    .courseId(itemShoppingCart.getCourseId())
                    .dateCreate(LocalDateTime.now())
                    .dateChange(LocalDateTime.now())
                    .changedBy(userService.getMyInfo().getId())
                    .isDeleted(false)
                    .build();
            orderDetailService.createOrderDetail(orderDetail);
<<<<<<< HEAD
            shoppingCartItemService.deleteShoppingCartItem(itemShoppingCart.getId());
        }

        var currentUser = userService.getMyInfo();
        var toEmail = currentUser.getEmail();
        var subject = "Thanh toán thành công trên trang eLearning";
        var customerName = currentUser.getFullName();
        var totalAmount = String.valueOf(orderAmount);
        var totalCourses = String.valueOf(shoppingCartItems.size());
        var authorizationCode = order.getId();
        LocalDateTime orderDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String orderDateString = orderDate.format(formatter);

        List<String> courseByNames = new ArrayList<>();

        for (var itemShoppingCart : shoppingCartItems) {
            courseByNames.add(itemShoppingCart.getCourse().getCourseName());
        }
        emailService.sendEmailPaymentAsync(toEmail, subject, customerName, totalAmount, totalCourses, authorizationCode, orderDateString, courseByNames);

=======
        }
>>>>>>> 1ef5b29a805965381a5e5a9f235252655d37f369
        return saveOrder;
    }

    @Transactional
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
    @Transactional
    public void softDeleteOrder(String id) {
        Order order = getOrderById(id);
        order.setDeleted(true);
        orderRepository.save(order);
    }
}
