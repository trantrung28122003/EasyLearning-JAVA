package com.hutech.easylearning.dto.reponse;

import com.hutech.easylearning.enums.DeletedInfoType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletedInfoResponse {
    String id;
    String name;
    LocalDateTime deletedDate;
    DeletedInfoType type;
    String detail;
}
