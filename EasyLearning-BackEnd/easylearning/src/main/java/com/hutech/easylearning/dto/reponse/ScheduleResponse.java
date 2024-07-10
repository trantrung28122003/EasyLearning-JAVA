package com.hutech.easylearning.dto.reponse;


import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.entity.CourseEvent;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleResponse {
    String courseId;
    String courseName;
    String avatarInstructor;
    List<CourseEventResponse> CourseEventResponse;
}
