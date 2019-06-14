package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.Tag;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class TagsDTO {
    private List<Tag> tags;
    private Long tagsTotalCount;

    public TagsDTO(Page<Tag> tagPage) {
        tags = tagPage.getContent();
        tagsTotalCount = tagPage.getTotalElements();
    }
}
