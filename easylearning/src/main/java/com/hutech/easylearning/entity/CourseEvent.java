package com.hutech.easylearning.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hutech.easylearning.enums.CourseEventType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "course_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "course_event_name")
    String eventName;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_event_type")
    CourseEventType eventType;

    @Column(name = "course_event_location")
    String location;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "course_event_date_start")
    LocalDateTime dateStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "course_event_date_end")
    LocalDateTime dateEnd;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;

    @OneToMany(mappedBy = "courseEvent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    Set<TrainingPart> trainingParts;
}
