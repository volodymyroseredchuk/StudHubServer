package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<Teacher> findAll();

    Optional<Teacher> findById(int id);

    Teacher save(Teacher teacher);

    Teacher update(Teacher teacher);

    void deleteById(int id);

    //List<Teacher> sortByAge();

    //List<Teacher> sortByMark();
}
