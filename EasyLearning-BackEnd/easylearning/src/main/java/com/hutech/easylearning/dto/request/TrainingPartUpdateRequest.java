package com.hutech.easylearning.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingPartUpdateRequest {

    String trainingPartName;

    LocalDateTime startTime;

    LocalDateTime endTime;

    String description;

    String trainingPartType;

    String imageUrl;

    String currentVideoUrl;

    String newVideoUrl;

    boolean isFree;

    String courseId;

    String courseEventId;

    LocalDateTime dateChange;

    String changedBy;

    boolean isDeleted;

    public boolean getFree() {
        return isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }
}
