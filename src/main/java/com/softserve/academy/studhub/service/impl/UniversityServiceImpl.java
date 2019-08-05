package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.UniversityRepository;
import com.softserve.academy.studhub.service.FileService;
import com.softserve.academy.studhub.service.UniversityService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;

@Service
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;
    private final FileService fileService;

    public UniversityServiceImpl(UniversityRepository universityRepository, FileService fileService) {
        this.universityRepository = universityRepository;
        this.fileService = fileService;
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
    public University save(University university) {
        university.setCreationDate(LocalDateTime.now());
        return universityRepository.saveAndFlush(university);
    }

    @Override
    public University update(University updatedUniversity) {

        University universityFromDB = findById(updatedUniversity.getId());

        universityFromDB.setName(updatedUniversity.getName());
        universityFromDB.setCity(updatedUniversity.getCity());
        universityFromDB.setImageUrl(updatedUniversity.getImageUrl());
        universityFromDB.setMark(updatedUniversity.getMark());

        return universityRepository.saveAndFlush(universityFromDB);
    }

    @Override
    public void delete(Integer universityId) {
        universityRepository.deleteById(universityId);
    }

    @Override
    public Integer addPhotoToUniversity(Integer universityId, MultipartFile multipartFile) throws IOException {
        University university = universityRepository.findById(universityId)
            .orElseThrow(() -> new NotFoundException(ErrorMessage.TEACHER_NOTFOUND));
        university.setImageUrl(fileService.uploadFile(multipartFile));
        universityRepository.saveAndFlush(university);
        return universityId;
    }

}
