package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.dto.TeacherDTO;
import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;

    private final ModelMapper modelMapper;


    public TeacherController(TeacherService teacherService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    @PreAuthorize("permitAll()")
    public List<Teacher> getAllTeachers() {
        return teacherService.findAll();
    }


//    @GetMapping
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<List<TeacherDTO>> getAllTeachers(Pageable pageable) {
//        Page<Teacher> teacherPage = teacherService.sortByAge(pageable);
//
//        List<TeacherDTO> teacherDTOs = teacherPage.getContent().stream()
//            .map(teacher -> modelMapper.map(teacher, TeacherDTO.class))
//            .collect(Collectors.toList());
//
//        return ResponseEntity.ok(teacherDTOs);
//    }

    @PostMapping(path = "/teacher")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TeacherDTO> addNewTeacher(@RequestBody TeacherDTO teacherDTO) {

        Teacher result = teacherService.save(modelMapper.map(teacherDTO, Teacher.class));
        return ResponseEntity.ok(modelMapper.map(result, TeacherDTO.class));
    }
}


