package com.hutech.easylearning.service;


import com.hutech.easylearning.entity.Feedback;
import com.hutech.easylearning.repository.FeedbackRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackService {

    FeedbackRepository feedbackRepository;

    @Transactional(readOnly = true)
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Feedback getFeedbackById(String id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
    }

    @Transactional
    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Transactional
    public Feedback updateFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Transactional
    public void deleteFeedback(String id) {
        feedbackRepository.deleteById(id);
    }
    @Transactional
    public void softDeleteFeedback(String id) {
        Feedback feedback = getFeedbackById(id);
        feedback.setDeleted(true);
        feedbackRepository.save(feedback);
    }
}

