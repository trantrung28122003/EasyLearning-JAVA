package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseEventRepository extends JpaRepository<CourseEvent, String> {
}
