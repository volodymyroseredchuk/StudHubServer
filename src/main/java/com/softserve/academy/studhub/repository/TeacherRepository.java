package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

//    Page<Teacher> findAllOrderByCreationDateDesc(Pageable pageable);
//
//    Page<Teacher> findAllOrderByMarkDesc(Pageable pageable);
}
