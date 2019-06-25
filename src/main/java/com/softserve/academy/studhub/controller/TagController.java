package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.TagsDTO;
import com.softserve.academy.studhub.service.TagService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<TagsDTO> getAllTagsSortedWithPagination(Pageable pageable) {
        return ResponseEntity.ok().body(new TagsDTO(tagService.findAllSorted(pageable)));
    }
}
