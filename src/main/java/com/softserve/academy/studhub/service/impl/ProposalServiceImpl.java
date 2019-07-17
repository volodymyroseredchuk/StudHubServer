package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Order;
import com.softserve.academy.studhub.entity.Proposal;
import com.softserve.academy.studhub.entity.Task;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.ProposalRepository;
import com.softserve.academy.studhub.service.OrderService;
import com.softserve.academy.studhub.service.ProposalService;
import com.softserve.academy.studhub.service.TaskService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ProposalServiceImpl implements ProposalService {

    private ProposalRepository proposalRepository;
    private UserService userService;
    private TaskService taskService;
    private OrderService orderService;

    @Override
    public Proposal save(Proposal proposal, Integer taskId, Principal principal) {

        proposal.setCreationDate(LocalDateTime.now());
        proposal.setUser(userService.findByUsername(principal.getName()));
        proposal.setTask(taskService.findById(taskId));

        return proposalRepository.saveAndFlush(proposal);
    }

    @Override
    public Proposal findById(Integer proposalId) {

        return proposalRepository.findById(proposalId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PROPOSAL_NOT_FOUND_BY_ID + proposalId));
    }

    @Override
    public void deleteById(Integer proposalId) {
        proposalRepository.deleteById(proposalId);
    }

    @Override
    public Page<Proposal> findAllByTaskId(Integer taskId, Pageable pageable) {

        return proposalRepository.findAllByTaskIdOrderByCreationDateDesc(taskId, pageable);
    }

    @Override
    @Transactional
    public Order approveProposal(Integer proposalId) {

        Proposal proposal = this.findById(proposalId);
        Task task = proposal.getTask();

        return orderService.create(task, proposal);
    }
}
