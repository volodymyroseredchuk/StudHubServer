package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.service.TeacherService;
import com.softserve.academy.studhub.service.UniversityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/universities")
public class UniversityController {





@Autowired
    private final UniversityService universityService;


   // UniversityController(UniversityService universityService) {
     //   this.universityService = universityService;
    //}

    @GetMapping
    List<University> findAllTeacher() {
        return universityService.findAll();
    }

    @GetMapping("/{id}")
    University updateOneUniversities(@PathVariable Integer id) {

        return universityService.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }
    @PostMapping
    University newTeacher(@RequestBody University newUniversity) {
        return universityService.save(newUniversity);
    }


    @PutMapping("/{id}")
    University replaceUniversity(@RequestBody University newUniversity, @PathVariable Integer id) {
        return universityService.update(newUniversity);
    }

    @DeleteMapping("/{id}")
    void deleteUniversity(@PathVariable Integer id) {
        universityService.deleteById(id);
    }
}
