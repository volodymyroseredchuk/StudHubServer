package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();

    Teacher findById(Integer teacherId);

    Teacher save(Teacher teacher);

    Teacher update(Integer teacherId, Teacher teacher);

    String deleteById(Integer teacherId);

//    Page<Teacher> sortByAge(Pageable pageable);
//
//    Page<Teacher> sortByMark(Pageable pageable);

}
