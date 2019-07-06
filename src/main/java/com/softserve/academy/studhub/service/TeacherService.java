package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();

    Teacher findById(Integer id);

    Teacher save(Teacher teacher);

    Teacher update(Integer teacherId, Teacher teacher);

    String deleteById(Integer teacherId);

    Page<Teacher> sortByAge(Pageable pageable);

    Page<Teacher> sortByMark(Pageable pageable);

}
