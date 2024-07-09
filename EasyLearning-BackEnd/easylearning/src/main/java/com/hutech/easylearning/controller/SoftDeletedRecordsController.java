package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.DeletedInfoResponse;
import com.hutech.easylearning.dto.reponse.RestoreRequest;
import com.hutech.easylearning.entity.CourseEvent;
import com.hutech.easylearning.enums.DeletedInfoType;
import com.hutech.easylearning.service.DeletedRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/RecycleBin")
public class SoftDeletedRecordsController {

    @Autowired
    DeletedRecordsService deletedRecordsService;
    @GetMapping
    public List<DeletedInfoResponse> getDeletedRecords() {
        return deletedRecordsService.getAllSoftDelete();
    }

    @PostMapping("/restore")
    public String restore(@RequestBody RestoreRequest restoreRequest) {
        deletedRecordsService.restoreDeletedRecord(restoreRequest);
        return "Record has been restored";
    }
}
