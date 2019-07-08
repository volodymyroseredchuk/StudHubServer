package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.service.UniversityService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/universities")
public class UniversityController {


    private UniversityService universityService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<University> getAllUniversities() {
        return universityService.findAll();
    }
}

