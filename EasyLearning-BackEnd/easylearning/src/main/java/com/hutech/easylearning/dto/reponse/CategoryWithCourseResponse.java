package com.hutech.easylearning.dto.reponse;


import com.hutech.easylearning.entity.Course;
import com.hutech.easylearning.enums.DeletedInfoType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryWithCourseResponse {
    String id;
    String categoryName;
    List<Course> courses;
}
