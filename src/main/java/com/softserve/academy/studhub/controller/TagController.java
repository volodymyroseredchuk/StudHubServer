package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.TagsDTO;
import com.softserve.academy.studhub.service.TagService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<TagsDTO> getAllTagsSortedWithPagination(Pageable pageable) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok().headers(respHeaders).body(tagService.findAllSorted(pageable));
    }
}
