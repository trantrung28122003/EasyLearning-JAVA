package com.hutech.easylearning.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "answer_content")
    String content;

    @Column(name ="answer_is_correct")
    boolean IsCorrect;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;

    @Column(name = "exercise_question_id", nullable = false)
    String questionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_question_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    ExerciseQuestion exerciseQuestion;
}
