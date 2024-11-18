package com.hutech.easylearning.controller;


import com.hutech.easylearning.dto.reponse.CompletedTrainingPartsResponse;
import com.hutech.easylearning.service.UserTrainingProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userTrainingProgress")
public class UserTrainingProgressController {

    @Autowired
    private UserTrainingProgressService userTrainingProgressService;

}

