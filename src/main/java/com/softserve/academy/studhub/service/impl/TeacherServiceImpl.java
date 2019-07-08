package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TeacherRepository;
import com.softserve.academy.studhub.service.TeacherService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
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
    public Teacher save(Teacher teacher) {
        teacher.setCreationDate(LocalDateTime.now());
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public Teacher update(Integer teacherId, Teacher teacher) {
        Teacher updatable = findById(teacherId);
        updatable.setFirstName(teacher.getFirstName());
        updatable.setLastName(teacher.getLastName());
        updatable.setImageUrl(teacher.getImageUrl());
        updatable.setMark(teacher.getMark());
        updatable.setModifiedDate(LocalDateTime.now());
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public String deleteById(Integer teacherId) {
        teacherRepository.deleteById(teacherId);
        return "Teacher was deleted";
    }

//    @Override
//    public Page<Teacher> sortByAge(Pageable pageable) {
//        return teacherRepository.findAllOrderByCreationDateDesc(pageable);
//    }
//
//    @Override
//    public Page<Teacher> sortByMark(Pageable pageable) {
//        return teacherRepository.findAllOrderByMarkDesc(pageable);
//    }

}
