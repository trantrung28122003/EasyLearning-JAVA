package com.hutech.easylearning.service;


import com.hutech.easylearning.entity.TrainerDetail;
import com.hutech.easylearning.repository.TrainerDetailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainerDetailService {

    TrainerDetailRepository trainerDetailRepository;

    @Transactional(readOnly = true)
    public List<TrainerDetail> getAllTrainerDetails() {
        return trainerDetailRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TrainerDetail getTrainerDetailById(String id) {
        return trainerDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TrainerDetail not found with id: " + id));
    }

    @Transactional
    public TrainerDetail createTrainerDetail(TrainerDetail trainerDetail) {
        return trainerDetailRepository.save(trainerDetail);
    }

    @Transactional
    public TrainerDetail updateTrainerDetail(TrainerDetail trainerDetail) {
        return trainerDetailRepository.save(trainerDetail);
    }

    @Transactional
    public void deleteTrainerDetail(String id) {
        trainerDetailRepository.deleteById(id);
    }

    @Transactional
    public void softDeleteTrainerDetail(String id) {
        TrainerDetail trainerDetail = getTrainerDetailById(id);
        trainerDetail.setDeleted(true);
        trainerDetail.setDateChange(LocalDateTime.now());
        trainerDetailRepository.save(trainerDetail);
    }
}

