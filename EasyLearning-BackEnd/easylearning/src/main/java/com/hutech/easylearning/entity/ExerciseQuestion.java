package com.hutech.easylearning.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "exercise_Question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExerciseQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "title")
    String title;

    @Column(name = "question")
    String question;

    @Column(name = "description")
    String description;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;

    @Column(name = "training_part_id")
    String trainingPartId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_part_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    TrainingPart trainingPart;

    @OneToMany(mappedBy = "exerciseQuestion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<Answer> answers;
}
