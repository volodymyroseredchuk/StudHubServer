package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.DeleteDTO;
import com.softserve.academy.studhub.dto.OrderDTO;
import com.softserve.academy.studhub.dto.ProposalDTO;
import com.softserve.academy.studhub.dto.ProposalPaginatedDTO;
import com.softserve.academy.studhub.entity.Proposal;
import com.softserve.academy.studhub.service.ProposalService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/tasks/{taskId}/proposals")
public class ProposalController {

    private ProposalService proposalService;
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProposalPaginatedDTO> getAllProposalsByTaskId(@PathVariable Integer taskId, Pageable pageable) {

        Page<Proposal> proposalPage = proposalService.findAllByTaskId(taskId, pageable);

        List<ProposalDTO> proposalDTOs = proposalPage.getContent().stream()
            .map(proposal -> modelMapper.map(proposal, ProposalDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new ProposalPaginatedDTO(proposalDTOs, proposalPage.getTotalElements()));
    }

    @GetMapping("/{proposalId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProposalDTO> getProposal(@PathVariable Integer proposalId) {

        Proposal proposal = proposalService.findById(proposalId);

        return ResponseEntity.ok().body(modelMapper.map(proposal, ProposalDTO.class));
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProposalDTO> createProposal(@Valid @RequestBody ProposalDTO proposalDTO,
                                                  @PathVariable Integer taskId,
                                                  Principal principal) {

        Proposal proposal = proposalService.save(modelMapper.map(proposalDTO, Proposal.class), taskId, principal);

        return ResponseEntity.ok().body(modelMapper.map(proposal, ProposalDTO.class));
    }

    @DeleteMapping("/{proposalId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
        "@proposalServiceImpl.findById(#proposalId).getUser().getUsername()== principal.username")
    public ResponseEntity<DeleteDTO> deleteProposal(@PathVariable Integer proposalId) {

        return ResponseEntity.ok().body(new DeleteDTO(proposalService.deleteById(proposalId)));

    }

    @PostMapping("/{proposalId}/approve")
    @PreAuthorize("@proposalServiceImpl.findById(#proposalId).getTask().getUser() == principal.username")
    public ResponseEntity<OrderDTO> approveProposal(@PathVariable Integer proposalId) {
        return ResponseEntity.ok().body(modelMapper.map(
                proposalService.approveProposal(proposalId),
                OrderDTO.class
        ));
    }
}
