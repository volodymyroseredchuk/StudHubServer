package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.repository.TeacherRepository;
import com.softserve.academy.studhub.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Teacher> findById(int id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public Teacher update(Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

   /* @Override
    public List<Teacher> sortByAge() {
        return teacherRepository.findAllByOrdeByCreationDateAsc();
    }

    @Override
    public List<Teacher> sortByMark() {
        return teacherRepository.findAllByTagListInOrderByMarkAsc();
    }*/

    @Override
    public void deleteById(int id) {
         teacherRepository.deleteById(id);
    }



}
