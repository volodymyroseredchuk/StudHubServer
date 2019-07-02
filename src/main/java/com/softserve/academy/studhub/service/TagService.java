package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.TagsDTO;
import com.softserve.academy.studhub.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface TagService {

    Tag save(Tag tag);

    Tag findById(Integer id);

    Tag findByName(String name);

    Page<Tag> findAllSorted(Pageable pageable);

    Set<Tag> reviewTagList(Set<Tag> tagList);

    Set<Tag> reviewTagList(String[] tags);
}
