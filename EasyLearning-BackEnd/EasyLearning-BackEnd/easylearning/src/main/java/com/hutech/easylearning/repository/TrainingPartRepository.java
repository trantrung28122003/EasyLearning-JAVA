package com.hutech.easylearning.repository;

import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.TrainingPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TransferQueue;

@Repository
public interface TrainingPartRepository extends JpaRepository<TrainingPart, String> {
    List<TrainingPart> findTrainingPartByCourseEventId (String courseEventId);
    List<TrainingPart> findTrainingPartByCourseId (String courseId);
}