package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.service.FileService;
import com.softserve.academy.studhub.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;

    @GetMapping
    List<Teacher> findAllTeacher() {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    Teacher updateOneTeacher(@PathVariable Integer id) {

        return teacherService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
    }
    @PostMapping
    Teacher newTeacher(@RequestBody Teacher newTeacher) {
        return teacherService.save(newTeacher);
    }


    @PutMapping("/{id}")
    Teacher replaceTeacher(@RequestBody Teacher newTeacher, @PathVariable Integer id) {
                    return teacherService.update(newTeacher);
    }

    @DeleteMapping("/{id}")
    void deleteTeacher(@PathVariable Integer id) {
        teacherService.deleteById(id);
    }

}
