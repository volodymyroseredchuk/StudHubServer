package com.softserve.academy.studhub.controller;

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
    public ResponseEntity<ProposalPaginatedDTO> getProposalsByTaskId(@PathVariable Integer taskId, Pageable pageable) {

        Page<Proposal> proposalPage = proposalService.findAllByTaskId(taskId, pageable);

        List<ProposalDTO> proposalDTOs = proposalPage.getContent().stream()
            .map(proposal -> modelMapper.map(proposal, ProposalDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new ProposalPaginatedDTO(proposalDTOs, proposalPage.getTotalElements()));
    }
}
