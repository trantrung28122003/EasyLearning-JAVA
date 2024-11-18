package com.hutech.easylearning.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_training_progress")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTrainingProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "training_part_id")
    String trainingPartId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_part_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    TrainingPart trainingPart;

    @Column(name = "user_id")
    String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    User user;

    @Column(name = "watched_duration")
    Integer watchedDuration;

    @Column(name = "quiz_score")
    Integer quizScore;

    @Column(name = "is_completed")
    boolean isCompleted;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;

}
