package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.TeacherDTO;
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
    List<Teacher> findAllByOrderByMarkDesc() {
        return teacherService.findAllByOrderByMarkDesc();
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
        List<TeacherDTO> teacherDTOS = result.getContent().stream()
            .map(teacher -> modelMapper.map(teacher, TeacherDTO.class))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(new TeacherPaginatedDTO(teacherDTOS,
            result.getTotalElements()));
    }

    @PostMapping("/teacher")
    @PreAuthorize("permitAll()")
//    @PreAuthorize("hasAuthority('TEACHER_WRITE_PRIVILEGE')")
    ResponseEntity<TeacherDTO> newTeacher(@RequestBody TeacherDTO teacherDTO) {
        teacherService.save(modelMapper.map(teacherDTO, Teacher.class));
        return ResponseEntity.ok(teacherDTO);
    }


    @DeleteMapping("/delete/{teacherId}")
    @PreAuthorize("hasAuthority('TEACHER_DELETE_ANY_PRIVILEGE')")
    ResponseEntity.BodyBuilder deleteTeacher(@PathVariable Integer teacherId) {
        teacherService.delete(teacherId);
        return ResponseEntity.status(200);
    }

    @PostMapping("/update")
    @PreAuthorize("permitAll()")
//    @PreAuthorize("hasAuthority('TEACHER_WRITE_PRIVILEGE')")
    ResponseEntity<TeacherDTO> updateTeacher(@RequestBody TeacherDTO teacherDTO) {
        Teacher result = teacherService.update(modelMapper.map(teacherDTO, Teacher.class));
        TeacherDTO resultDTO = modelMapper.map(result, TeacherDTO.class);
        return ResponseEntity.ok(resultDTO);
    }
}
