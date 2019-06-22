package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.dto.TagsDTO;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.repository.TagRepository;
import com.softserve.academy.studhub.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.saveAndFlush(tag);
    }

    @Override
    public Tag findById(Integer id) {
        return tagRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Tag is not found"));
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByNameIgnoreCase(name).orElseThrow(
            () -> new IllegalArgumentException("Tag is not found"));
    }

    @Override
    public Tag update(Tag tag) {
        return tagRepository.saveAndFlush(tag);
    }

    @Override
    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable).getContent();
    }

    @Override
    public Page<Tag> findAllSorted(Pageable pageable) {
        return tagRepository.findAllSorted(pageable);
    }

    @Override
    public List<Tag> reviewTagList(List<Tag> tagList) {
        if (tagList == null) {
            return null;
        }
        List<Tag> dbTagsList = new ArrayList<>();
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
    public List<Tag> reviewTagList(String[] tags) {
        if (tags == null) {
            return new ArrayList<>();
        }
        List<Tag> dbTagsList = new ArrayList<>();
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
