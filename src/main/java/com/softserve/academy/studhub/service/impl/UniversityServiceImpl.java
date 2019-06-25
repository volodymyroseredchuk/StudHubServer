package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;
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
    public Optional<University> findById(int id) {
        return universityRepository.findById(id);
    }

    @Override
    public University save(University teacher) {
        return universityRepository.saveAndFlush(teacher);
    }

    @Override
    public University update(University university) {
        return universityRepository.saveAndFlush(university);
    }

  /*  @Override
    public List<University> sortByAge() {
        return universityRepository.findAllByOrdeByCreationDateAsc();
    }

    @Override
    public List<University> sortByMark() {
        return universityRepository.findAllByTagListInOrderByMarkAsc();
    }*/

    @Override
    public void deleteById(int id) {
        universityRepository.deleteById(id);
    }

}
