package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<Teacher> findAll();

    Teacher findById(Integer teacherId);

    Teacher save(Teacher teacher);

    Teacher update(Teacher teacher);

    void deleteById(int id);

    Integer addPhotoToTeacher(Integer teacherId, MultipartFile multipartFile);

    Page<Teacher> findByLastName (String keyword, Pageable pageable);

    //List<Teacher> sortByAge();

    //List<Teacher> sortByMark();
}
