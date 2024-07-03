package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
