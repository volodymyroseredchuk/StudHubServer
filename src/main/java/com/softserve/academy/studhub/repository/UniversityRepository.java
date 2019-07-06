package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.University;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface UniversityRepository extends JpaRepository <University, Integer> {
    Page<University> findAllOrderByCreationDateDesc(Pageable pageable);

    Page<University> findAllOrderByMarkDesc(Pageable pageable);
}
