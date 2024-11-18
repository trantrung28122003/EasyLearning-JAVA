package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.UserTrainingProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTrainingProgressRepository extends JpaRepository<com.hutech.easylearning.entity.UserTrainingProgress, String> {
    List<UserTrainingProgress> findByTrainingPartIdIn(List<String> trainingPartIds);
    UserTrainingProgress findByUserIdAndTrainingPartId(String userId, String trainingPartId);
}

