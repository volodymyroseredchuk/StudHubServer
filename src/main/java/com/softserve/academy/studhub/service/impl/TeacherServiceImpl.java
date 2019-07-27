package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TeacherRepository;
import com.softserve.academy.studhub.service.FileService;
import com.softserve.academy.studhub.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final FileService fileService;

    public TeacherServiceImpl(TeacherRepository teacherRepository, FileService fileService) {
        this.teacherRepository = teacherRepository;
        this.fileService = fileService;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findById(Integer teacherId) throws NotFoundException {
        return teacherRepository.findById(teacherId).orElseThrow(
            () -> new NotFoundException(ErrorMessage.TEACHER_NOTFOUND + teacherId));
    }

    @Override
    public Page<Teacher> findByLastName(String keyword, Pageable pageable) {
        return teacherRepository.findByLastName(keyword, pageable);
    }


    @Override
    public Teacher save(Teacher teacher) {
        teacher.setCreationDate(LocalDateTime.now());
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public Teacher update(Teacher updatedTeacher) {

        Teacher teacherFromDB = findById(updatedTeacher.getId());

        teacherFromDB.setFirstName(updatedTeacher.getFirstName());
        teacherFromDB.setLastName(updatedTeacher.getLastName());
        teacherFromDB.setImageUrl(updatedTeacher.getImageUrl());
        teacherFromDB.setUniversity(updatedTeacher.getUniversity());
        teacherFromDB.setMark(updatedTeacher.getMark());

        return teacherRepository.saveAndFlush(teacherFromDB);
    }

    @Override
    public void delete(Integer teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public Integer addPhotoToTeacher(Integer teacherId, MultipartFile multipartFile) throws IOException {
        Teacher teacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new NotFoundException(ErrorMessage.TEACHER_NOTFOUND));
        teacher.setImageUrl(fileService.uploadFile(multipartFile));
        teacherRepository.saveAndFlush(teacher);
        return teacherId;
    }
}
