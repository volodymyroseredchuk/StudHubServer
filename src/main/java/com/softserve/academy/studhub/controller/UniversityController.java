package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.service.UniversityService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/universities")
public class UniversityController {


    private UniversityService universityService;


    @GetMapping
    @PreAuthorize("permitAll()")
    List<University> findAllUniversity() {
        return universityService.findAll();
    }

    @GetMapping("/{universityId}")
    @PreAuthorize("permitAll()")
    public University getUniversityById(@PathVariable Integer universityId) {
        return universityService.findById(universityId);
    }
}
