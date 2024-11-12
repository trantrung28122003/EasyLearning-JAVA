package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.FeedbackInfoResponse;
import com.hutech.easylearning.dto.reponse.FeedbackResponse;
import com.hutech.easylearning.dto.request.FeedbackCreationRequest;
import com.hutech.easylearning.dto.request.FeedbackUpdateRequest;
import com.hutech.easylearning.entity.Feedback;
import com.hutech.easylearning.exception.AppException;
import com.hutech.easylearning.exception.ErrorCode;
import com.hutech.easylearning.repository.FeedbackRepository;
import com.hutech.easylearning.repository.OrderDetailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackService {

    FeedbackRepository feedbackRepository;

    OrderDetailRepository orderDetailRepository;

    @Autowired
    UserService userService;

    @Transactional(readOnly = true)
    public Feedback getFeedbackById(String id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
    }


    @Transactional(readOnly = true)
    public FeedbackResponse getFeedbacksByCourse(String courseId) {
        List<FeedbackInfoResponse> feedbackInfos = new ArrayList<>();
        var feedbacks = feedbackRepository.findByCourseId(courseId);
        var currentUser = userService.getMyInfo();
        var hasBought = orderDetailRepository.existsByOrderUserIdAndCourseId(currentUser.getId(), courseId);
        for (var feedback : feedbacks) {
            var typeUser = feedback.getFeedbackUserId().equals(currentUser.getId()) ? "Bạn" : "Khách hàng";
            FeedbackInfoResponse feedbackResponse = FeedbackInfoResponse.builder()
                    .feedbackId(feedback.getId())
                    .courseId(feedback.getCourseId())
                    .userId(feedback.getFeedbackUserId())
                    .content(feedback.getFeedbackContent())
                    .fullNameUser(currentUser.getFullName())
                    .typeUser(typeUser)
                    .avatar(currentUser.getImageUrl())
                    .dateChange(feedback.getDateChange())
                    .build();
            feedbackInfos.add(feedbackResponse);
            if(feedback.getFeedbackUserId().equals(currentUser.getId())) {
                hasBought = false;
            }
        }

        FeedbackResponse feedbackResponse = FeedbackResponse.builder()
                .feedbacks(feedbackInfos)
                .setHasGivenFeedback(hasBought)
                .build();
        return feedbackResponse;
    }

    @Transactional
    public Feedback createFeedback(FeedbackCreationRequest request) {
        var currentUser = userService.getMyInfo();
        Feedback feedback = Feedback.builder()
                .feedbackContent(request.getFeedbackContent())
                .feedbackRating(request.getFeedbackRating())
                .feedbackUserId(currentUser.getId())
                .courseId(request.getCourseId())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(currentUser.getId())
                .isDeleted(false)
                .build();
        return feedbackRepository.save(feedback);
    }

    @Transactional

    public Feedback updateFeedback(String feedbackId, FeedbackUpdateRequest request) {
        var feedback = getFeedbackById(feedbackId);
        var currentUser = userService.getMyInfo();

        if (!feedback.getFeedbackUserId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.UPDATE_OWN_FEEDBACK_ONLY);
        }
        feedback.setFeedbackContent(request.getFeedbackContent());
        feedback.setFeedbackRating(request.getFeedbackRating());
        feedback.setDateChange(LocalDateTime.now());
        feedback.setChangedBy(currentUser.getId());
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

