package com.softserve.academy.studhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProposalPaginatedDTO {

    private List<ProposalDTO> proposals;

    private Long proposalsTotalCount;
}
