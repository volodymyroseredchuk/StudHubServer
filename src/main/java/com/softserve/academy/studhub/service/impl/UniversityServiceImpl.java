package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.UniversityRepository;
import com.softserve.academy.studhub.service.UniversityService;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

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
    public University findById(Integer universityId) throws NotFoundException {
        return universityRepository.findById(universityId).orElseThrow(
            () -> new NotFoundException(ErrorMessage.UNIVERSITY_NOTFOUND + universityId));
    }

    @Override
    public University save(University teacher) {
        return universityRepository.saveAndFlush(teacher);
    }

//    @Override
//    public List<University> sortByMark() {
//        return universityRepository.findAllByTagListInOrderByMarkAsc();
//    }

}
