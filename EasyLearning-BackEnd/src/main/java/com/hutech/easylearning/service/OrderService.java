package com.hutech.easylearning.service;


import com.hutech.easylearning.entity.Order;
import com.hutech.easylearning.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;

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
    public Order createOrder(Order order) {
        return orderRepository.save(order);
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
