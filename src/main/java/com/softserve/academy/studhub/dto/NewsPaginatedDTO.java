package com.softserve.academy.studhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NewsPaginatedDTO {

    private List<NewsForListDTO> newsList;

    private Long newsTotalCount;

}
