package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.dto.TeacherForListDTO;
import com.softserve.academy.studhub.dto.TeacherPaginatedDTO;
import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.service.TeacherService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("permitAll()")
    List<Teacher> findAllTeacher() {
        return teacherService.findAll();
    }

    @GetMapping("/{teacherId}")
    @PreAuthorize("permitAll()")
    public Teacher getTeacherById(@PathVariable Integer teacherId) {
        return teacherService.findById(teacherId);
    }

    @GetMapping("/teachersByLastName/{keyword}")
    @PreAuthorize("permitAll()")
    ResponseEntity<TeacherPaginatedDTO> findTeachersByLastName(@PathVariable String keyword,
                                                               Pageable pageable) {
        Page<Teacher> result = teacherService.findByLastName(keyword, pageable);
        List<TeacherForListDTO> teacherForListDTOS = result.getContent().stream()
            .map(teacher -> modelMapper.map(teacher, TeacherForListDTO.class))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(new TeacherPaginatedDTO(teacherForListDTOS,
            result.getTotalElements()));
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    Teacher newTeacher(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }

    @PostMapping("/addPhotoToTeacher")
    @PreAuthorize("permitAll()")
    ResponseEntity<Integer> addPhotoToTeacher(@RequestParam Integer teacherId,
                                              @RequestParam MultipartFile multipartFile) throws IOException {
        Integer result = teacherService.addPhotoToTeacher(teacherId, multipartFile);
        return ResponseEntity.ok(result);
    }

}
