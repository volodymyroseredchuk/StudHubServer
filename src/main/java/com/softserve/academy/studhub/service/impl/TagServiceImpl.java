package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.repository.TagRepository;
import com.softserve.academy.studhub.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Tag> resultTag = tagRepository.findById(id);
        if (resultTag.isPresent()) {
            return resultTag.get();
        }
        throw new IllegalArgumentException("Tag is not found");
    }

    @Override
    public Tag findByName(String name) {
        Optional<Tag> resultTag = tagRepository.findByName(name);
        if (resultTag.isPresent()) {
            return resultTag.get();
        }
        throw new IllegalArgumentException("Tag is not found");
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
}
