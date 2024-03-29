package com.softserve.academy.studhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionPaginatedDTO {

    private List<QuestionForListDTO> questions;

    private Long questionsTotalCount;
}
