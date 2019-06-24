package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;

import java.util.List;
import java.util.Optional;

public interface UniversityService {
    List<University> findAll();

    Optional<University> findById(int id);

    University save(University university);

    University update(University university);

    void deleteById(int id);

   // List<University> sortByAge();

    //List<University> sortByMark();
}
