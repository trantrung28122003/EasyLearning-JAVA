package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    List<Certificate> findAllByUserId(String userId);
    Optional<Certificate> findByUserIdAndCourseId(String userId, String courseId);
}