package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.service.TagService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAllTagsSortedWithPagination(Pageable pageable) {
        return tagService.findAllSorted(pageable);
    }
}
