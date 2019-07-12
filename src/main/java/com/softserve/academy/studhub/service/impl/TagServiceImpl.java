package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.dto.TagsDTO;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.repository.TagRepository;
import com.softserve.academy.studhub.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;


    @Override
    public Tag save(Tag tag) {

        return tagRepository.saveAndFlush(tag);
    }

    @Override
    public Tag findById(Integer id) {

        return tagRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException(ErrorMessage.TAG_NOT_FOUND_BY_ID + id));
    }

    @Override
    public Tag findByName(String name) {

        return tagRepository.findByNameIgnoreCase(name).orElseThrow(
            () -> new IllegalArgumentException(ErrorMessage.TAG_NOT_FOUND_BY_NAME + name));
    }

    @Override
    public Page<Tag> findAllSorted(Pageable pageable) {

        return tagRepository.findAllSorted(pageable);
    }

    @Override
    public Set<Tag> reviewTagList(Set<Tag> tagList) {

        if (tagList == null) {
            return new HashSet<>();
        }
        Set<Tag> dbTagsList = new HashSet<>();
        Tag tempTag;
        for (Tag tag : tagList) {
            try {
                tempTag = findByName(tag.getName());
            } catch (IllegalArgumentException e) {
                tempTag = save(tag);
            }
            dbTagsList.add(tempTag);
        }
        return dbTagsList;
    }

    @Override
    public Set<Tag> reviewTagList(String[] tags) {

        if (tags == null) {
            return new HashSet<>();
        }
        Set<Tag> dbTagsList = new HashSet<>();
        Tag tempTag;
        for (String tag : tags) {
            try {
                tempTag = findByName(tag);
            } catch (IllegalArgumentException e) {
                continue;
            }

            dbTagsList.add(tempTag);
        }
        return dbTagsList;
    }
}
