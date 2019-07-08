package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.UniversityRepository;
import com.softserve.academy.studhub.service.UniversityService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public List<University> findAll() {
        return universityRepository.findAll();
    }

    @Override
    public University findById(Integer universityId) {
        return universityRepository.findById(universityId).orElseThrow(
            () -> new NotFoundException(ErrorMessage.UNIVERSITY_NOTFOUND + universityId));
    }

    @Override
    public University save(University university) {
        university.setCreationDate(LocalDateTime.now());
        return universityRepository.saveAndFlush(university);
    }

    @Override
    public University update(Integer universityId, University university) {
        University updatable = findById(universityId);
        updatable.setImageUrl(university.getImageUrl());
        updatable.setMark(university.getMark());
        updatable.setModifiedDate(LocalDateTime.now());
        return universityRepository.saveAndFlush(university);
    }

    @Override
    public String deleteById(Integer universityId) {
        universityRepository.deleteById(universityId);
        return "University was deleted";
    }

//    @Override
//    public Page<University> sortByAge(Pageable pageable) {
//        return universityRepository.findAllOrderByCreationDateDesc(pageable);
//    }
//
//    @Override
//    public Page<University> sortByMark(Pageable pageable) {
//        return universityRepository.findAllOrderByMarkDesc(pageable);
//    }
}
