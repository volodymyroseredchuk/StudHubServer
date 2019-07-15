package com.softserve.academy.studhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamPaginatedDTO {

    private List<TeamForListDTO> teams;

    private Long teamsTotalCount;
}
