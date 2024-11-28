package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.reponse.CommentResponse;
import com.hutech.easylearning.dto.reponse.ReplyResponse;
import com.hutech.easylearning.dto.request.CommentRequest;
import com.hutech.easylearning.dto.request.ReplyRequest;
import com.hutech.easylearning.entity.Comment;
import com.hutech.easylearning.entity.Reply;
import com.hutech.easylearning.repository.CommentRepository;
import com.hutech.easylearning.repository.ReplyRepository;
import com.hutech.easylearning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<CommentResponse> getCommentsByTrainingPartId(String trainingPartId) {
        List<CommentResponse> commentResponseList = new ArrayList<>();

        var currentUser = userService.getMyInfo();
        List<Comment> comments = commentRepository.findAllByTrainingPartId(trainingPartId);
        for(Comment comment : comments) {
            List<ReplyResponse> replyResponseList = new ArrayList<>();
            for(Reply reply : comment.getReplies()) {
                ReplyResponse replyResponse = new ReplyResponse().builder()
                        .id(reply.getId())
                        .contentReply(reply.getContent())
                        .commentId(comment.getId())
                        .userId(reply.getUserId())
                        .userFullName(currentUser.getFullName())
                        .userImageUrl(currentUser.getImageUrl())
                        .dateCreate(reply.getDateCreate())
                        .build();
                replyResponseList.add(replyResponse);
            }
            CommentResponse commentResponse = new CommentResponse().builder()
                    .id(comment.getId())
                    .contentComment(comment.getContent())
                    .userId(comment.getUserId())
                    .userFullName(currentUser.getFullName())
                    .userImageUrl(currentUser.getImageUrl())
                    .dateCreate(comment.getDateCreate())
                    .replies(replyResponseList)
                    .build();


            commentResponseList.add(commentResponse);
        }
        return commentResponseList;
    }

    public CommentResponse addComment(CommentRequest request) {
        var userById = userRepository.findById(request.getUserId());
        Comment comment = new Comment().builder()
                .content(request.getCommentContent())
                .trainingPartId(request.getTrainingPartId())
                .userId(request.getUserId())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(request.getUserId())
                .build();
       commentRepository.save(comment);
       CommentResponse commentResponse = new CommentResponse().builder()
               .id(comment.getId())
               .contentComment(comment.getContent())
               .userId(comment.getUserId())
               .userFullName(userById.get().getFullName())
               .userImageUrl(userById.get().getImageUrl())
               .dateCreate(comment.getDateCreate())
               .replies(new ArrayList<>())
               .build();

        return commentResponse;
    }



    public ReplyResponse addReplyToComment(ReplyRequest request) {
        var userById = userRepository.findById(request.getUserId());
        Reply reply = new Reply().builder()

                .content(request.getReplyContent())
                .userId(request.getUserId())
                .dateCreate(LocalDateTime.now())
                .dateChange(LocalDateTime.now())
                .changedBy(request.getReplyContent())
                .commentId(request.getCommentId())
                .build();
        replyRepository.save(reply);
        ReplyResponse replyResponse = new ReplyResponse().builder()
                .id(reply.getId())
                .commentId(reply.getCommentId())
                .contentReply(reply.getContent())
                .userId(reply.getUserId())
                .userFullName(userById.get().getFullName())
                .userImageUrl(userById.get().getImageUrl())
                .dateCreate(reply.getDateCreate())
                .build();
        return replyResponse;
    }
}

