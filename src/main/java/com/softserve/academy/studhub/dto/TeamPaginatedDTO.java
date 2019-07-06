package com.softserve.academy.studhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeamPaginatedDTO {

    private List<TeamForListDTO> teams;

    private Long teamsTotalCount;
}
