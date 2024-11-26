package com.hutech.easylearning.service;


import com.hutech.easylearning.dto.reponse.AnswerResponse;
import com.hutech.easylearning.dto.reponse.ExerciseResponse;
import com.hutech.easylearning.entity.Answer;
import com.hutech.easylearning.entity.ExerciseQuestion;
import com.hutech.easylearning.repository.ExerciseQuestionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExerciseQuestionService {

    @Autowired
    ExerciseQuestionRepository exerciseQuestionRepository;

    public List<ExerciseQuestion> getExerciseQuestionsByTrainingPartId(String trainingPartId) {
        return exerciseQuestionRepository.findByTrainingPartId(trainingPartId);
    }


    public List<ExerciseResponse> getExerciseByTrainingPart(String trainingPartId) {
        List<ExerciseResponse> exerciseResponseList = new ArrayList<>();
        List<ExerciseQuestion> exerciseQuestionList = exerciseQuestionRepository.findByTrainingPartId(trainingPartId);

        for (var exerciseQuestion : exerciseQuestionList) {

            List<AnswerResponse> answerResponses = new ArrayList<>();

            for(var answer : exerciseQuestion.getAnswers()) {
                AnswerResponse answerResponse = new AnswerResponse().builder()
                        .content(answer.getContent())
                        .isCorrect(answer.isIsCorrect())
                        .build();
                answerResponses.add(answerResponse);
            }
            ExerciseResponse exerciseResponse = new ExerciseResponse().builder()
                    .id(exerciseQuestion.getId())
                    .questionText(exerciseQuestion.getQuestion())
                    .answerResponses(answerResponses)
                    .build();

            exerciseResponseList.add(exerciseResponse);
        }
        return exerciseResponseList;
    }

}
