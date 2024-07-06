package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    boolean existsByOrderUserIdAndCourseId(String userId, String courseId);
}
