package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Order;
import com.softserve.academy.studhub.entity.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface ProposalService {

    Proposal save(Proposal proposal, Integer taskId, Principal principal);

    Proposal findById(Integer proposalId);

    String deleteById(Integer proposalId);

    Page<Proposal> findAllByTaskId(Integer taskId, Pageable pageable);

    Order approveProposal(Integer proposalId);
}
