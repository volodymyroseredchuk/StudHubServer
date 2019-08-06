package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UniversityService {
    List<University> findAllByOrderByMarkDesc();

    University findById(Integer universityId);

    University save(University university);

    University update(University updatedUniversity);

    void delete(Integer universityId);

    Integer addPhotoToUniversity(Integer universityId, MultipartFile multipartFile) throws IOException;
}
