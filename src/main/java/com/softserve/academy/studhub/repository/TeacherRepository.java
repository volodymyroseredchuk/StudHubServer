package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Teacher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository <Teacher, Integer> {

    Page<Teacher> findByLastName(String lastName, Pageable pageable);

   List<Teacher> findAllByOrderByMarkDesc ();
}
