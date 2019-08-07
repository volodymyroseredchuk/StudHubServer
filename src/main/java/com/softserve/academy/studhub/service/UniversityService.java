package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.University;

import java.util.List;

public interface UniversityService {
    List<University> findAllByOrderByMarkDesc();

    University findById(Integer universityId);

    University save(University university);

    University update(University updatedUniversity);

    void delete(Integer universityId);
}
