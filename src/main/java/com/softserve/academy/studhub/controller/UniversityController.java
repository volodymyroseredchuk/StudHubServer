package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.service.TeacherService;
import com.softserve.academy.studhub.service.UniversityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<University> findAllTeacher() {
        return universityService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    University updateOneUniversities(@PathVariable Integer id) {

        return universityService.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    University newTeacher(@RequestBody University newUniversity) {
        return universityService.save(newUniversity);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    University replaceUniversity(@RequestBody University newUniversity, @PathVariable Integer id) {
        return universityService.update(newUniversity);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteUniversity(@PathVariable Integer id) {
        universityService.deleteById(id);
    }
}
