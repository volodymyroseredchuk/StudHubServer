package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAllByOrderByMarkDesc();

    Teacher findById(Integer teacherId);

    Teacher save(Teacher teacher);

    Teacher update(Teacher teacher);

    void delete(Integer teacherId);

    Page<Teacher> findByLastName(String keyword, Pageable pageable);

}
