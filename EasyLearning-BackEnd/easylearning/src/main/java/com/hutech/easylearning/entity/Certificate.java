package com.hutech.easylearning.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "certificate")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id", columnDefinition = "VARCHAR(36)")
    String id;

    @Column(name = "user_id", nullable = false)
    String userId;

    @Column(name = "course_id", nullable = false)
    String courseId;

    @Column(name = "certificate_url", nullable = false)
    String certificateUrl;

    @Column(name = "issued_date", nullable = false)
    LocalDateTime issuedDate;

    @Column(name = "certificate_number", nullable = false)
    String certificateNumber;

    @Column(name = "expiration_date")
    LocalDateTime expirationDate;

    @Column(name = "date_create")
    LocalDateTime dateCreate;

    @Column(name = "date_change")
    LocalDateTime dateChange;

    @Column(name = "changed_by")
    String changedBy;

    @Column(name = "is_deleted")
    boolean isDeleted;
}
