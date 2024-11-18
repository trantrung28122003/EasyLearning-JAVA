package com.hutech.easylearning.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hutech.easylearning.enums.TrainingPartType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "training_part")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingPart{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "training_part_name")
    String trainingPartName;

    @Column(name = "training_part_start_time")
    LocalDateTime startTime;

    @Column(name = "training_part_end_time")
    LocalDateTime endTime;

    @Column(name = "training_part_sescription")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_part_type")
    TrainingPartType trainingPartType;

    @Column(name = "training_part_imageUrl")
    String imageUrl;

    @Column(name = "training_part_videoUrl")
    String videoUrl;

    @Column(name = "training_part_isFree")
    boolean isFree;

    @Column(name = "training_part_course_id")
    String courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_part_course_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    Course course;

    @Column(name = "training_part_event_id")
    String courseEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_part_event_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    CourseEvent courseEvent;


    @OneToMany(mappedBy = "trainingPart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<UserTrainingProgress> userTrainingProgress;


    @OneToMany(mappedBy = "trainingPart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<Comment> comments;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;
}

