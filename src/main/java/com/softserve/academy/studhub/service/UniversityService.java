package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.University;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface UniversityService {
    List<University> findAll();

    University findById(Integer universityId);

    University save(University university);

    University update(Integer universityId, University university);

    String deleteById(Integer universityId);

//    Page<University> sortByAge(Pageable pageable);
//
//    Page<University> sortByMark(Pageable pageable);

}
