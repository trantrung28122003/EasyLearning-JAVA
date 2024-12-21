package com.hutech.easylearning.service;


import com.hutech.easylearning.entity.OrderDetail;
import com.hutech.easylearning.repository.OrderDetailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailService {

    final OrderDetailRepository orderDetailRepository;

    @Transactional(readOnly = true)
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderDetail getOrderDetailById(String id) {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found with id: " + id));
    }

    @Transactional
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Transactional
    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Transactional
    public void deleteOrderDetail(String id) {
        orderDetailRepository.deleteById(id);
    }

    @Transactional
    public void softDeleteOrderDetail(String id) {
        OrderDetail orderDetail = getOrderDetailById(id);
        orderDetail.setDeleted(true);
        orderDetailRepository.save(orderDetail);
    }

}

