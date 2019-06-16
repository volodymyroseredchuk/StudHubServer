package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.TagsDTO;
import com.softserve.academy.studhub.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag save(Tag tag);

    Tag findById(Integer id);

    Tag findByName(String name);

    Tag update(Tag tag);

    void deleteById(Integer id);

    List<Tag> findAll();

    List<Tag> findAll(Pageable pageable);

    TagsDTO findAllSorted(Pageable pageable);

    List<Tag> reviewTagList(List<Tag> tagList);
}
