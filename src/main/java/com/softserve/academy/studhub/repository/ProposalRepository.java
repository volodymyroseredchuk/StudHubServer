package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

    Page<Proposal> findAllByTaskIdOrderByCreationDateDesc(Integer taskId, Pageable pageable);
}
