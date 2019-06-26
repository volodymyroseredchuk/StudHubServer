package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository <Teacher, Integer> {
   // List<Teacher> findAllByOrdeByCreationDateAsc();

   // List<Teacher> findAllByTagListInOrderByMarkAsc();

}
