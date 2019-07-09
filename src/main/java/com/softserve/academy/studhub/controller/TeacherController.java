package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.service.FileService;
import com.softserve.academy.studhub.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("permitAll()")
    List<Teacher> findAllTeacher() {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    Teacher updateOneTeacher(@PathVariable Integer id) {

        return teacherService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER_WRITE_PRIVILEGE')")
    Teacher newTeacher(@RequestBody Teacher newTeacher) {
        return teacherService.save(newTeacher);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER_WRITE_PRIVILEGE')")
    Teacher replaceTeacher(@RequestBody Teacher newTeacher, @PathVariable Integer id) {
                    return teacherService.update(newTeacher);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER_DELETE_ANY_PRIVILEGE')")
    void deleteTeacher(@PathVariable Integer id) {
        teacherService.deleteById(id);
    }

}
