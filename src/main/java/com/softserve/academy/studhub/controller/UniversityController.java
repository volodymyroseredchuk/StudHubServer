package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.UniversityDTO;
import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.service.UniversityService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/universities")
public class UniversityController {


    private UniversityService universityService;
    private ModelMapper modelMapper;


    @GetMapping
    @PreAuthorize("permitAll()")
    List<University> findAllByOrderByMarkDesc() {
        return universityService.findAllByOrderByMarkDesc();
    }

    @GetMapping("/{universityId}")
    @PreAuthorize("permitAll()")
    public University getUniversityById(@PathVariable Integer universityId) {
        return universityService.findById(universityId);
    }

    @PostMapping("/university")
    @PreAuthorize("permitAll()")
//    @PreAuthorize("hasAuthority('UNIVERSITY_WRITE_PRIVILEGE')")
    ResponseEntity<UniversityDTO> newUniversity(@RequestBody UniversityDTO universityDTO) {
        universityService.save(modelMapper.map(universityDTO, University.class));
        return ResponseEntity.ok(universityDTO);
    }


    @DeleteMapping("/{universityId}")
//    @PreAuthorize("hasAuthority('UNIVERSITY_DELETE_ANY_PRIVILEGE')")
    ResponseEntity.BodyBuilder deleteUniversity(@PathVariable Integer universityId) {
        universityService.delete(universityId);
        return ResponseEntity.status(200);
    }

    @PutMapping("/{universityId}/update")
    @PreAuthorize("permitAll()")
//    @PreAuthorize("hasAuthority('TEACHER_WRITE_PRIVILEGE')")
    ResponseEntity<UniversityDTO> updateUniversity(@RequestBody UniversityDTO universityDTO) {
        University result = universityService.update(modelMapper.map(universityDTO, University.class));
        UniversityDTO resultDTO = modelMapper.map(result, UniversityDTO.class);
        return ResponseEntity.ok(resultDTO);
    }

}
