package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeacherService {
    List<Teacher> findAllOrderByMarkDesc();

    Teacher findById(Integer teacherId);

    Teacher save(Teacher teacher);

    Teacher update(Teacher teacher);

    void delete(Integer teacherId);

    Integer addPhotoToTeacher(Integer teacherId, MultipartFile multipartFile) throws IOException;

    Page<Teacher> findByLastName(String keyword, Pageable pageable);

}
