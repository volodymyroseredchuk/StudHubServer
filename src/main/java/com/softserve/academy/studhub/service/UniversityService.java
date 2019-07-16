package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;

import java.util.List;
import java.util.Optional;

public interface UniversityService {
    List<University> findAll();

    University save(University university);

    University findById(Integer universityId);

    //List<University> sortByMark();
}
