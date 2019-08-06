package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    Page<Team> findAllByIsPublicTrueOrderByCreationDateDesc(Pageable pageable);

    List<Team> findAllByIsPublicTrueOrderByCreationDateDesc();

    List<Team> findAllByIsPublicFalseOrderByCreationDateDesc();
}
