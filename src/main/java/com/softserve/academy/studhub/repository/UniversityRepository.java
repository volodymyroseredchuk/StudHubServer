package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityRepository extends JpaRepository <University, Integer> {

   // List<University> findAllByTagListInOrderByMarkAsc();

}
