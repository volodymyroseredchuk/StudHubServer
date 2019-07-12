package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.repository.TagRepository;
import com.softserve.academy.studhub.service.impl.TagServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagServiceMockTest {

    @Mock
    private TagRepository tagRepository;

    private TagService tagService;

    @Before
    public void init() {
        tagService = new TagServiceImpl(tagRepository);
    }


    @Test
    public void saveTagTest() {
        Tag inputTag = new Tag();
        inputTag.setName("test");

        Tag outputTag = new Tag();
        outputTag.setName("test");
        outputTag.setId(1);

        when(tagRepository.saveAndFlush(inputTag)).thenReturn(outputTag);

        Tag resultTag = tagService.save(inputTag);

        verify(tagRepository, times(1)).saveAndFlush(any());

        assertNotEquals("New param2 should not be 0", Integer.valueOf(0), resultTag.getId());
        assertEquals("Names should be the same", resultTag.getName(), inputTag.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTagByIdNullTest() {
        tagService.findById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTagByIdNotFoundTest() {
        when(tagRepository.findById(any())).thenThrow(IllegalArgumentException.class);
        tagService.findById(1);
    }

    @Test
    public void findTagByIdTest() {
        Integer id = 1;
        Tag outputTag = new Tag();
        outputTag.setId(id);

        when(tagRepository.findById(id)).thenReturn(Optional.of(outputTag));

        Tag resultTag = tagService.findById(id);

        verify(tagRepository, times(1)).findById(any());

        assertEquals("IDs should be the same", id, resultTag.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTagByNameNullTest() {
        tagService.findByName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTagByNameNotFoundTest() {
        when(tagRepository.findByNameIgnoreCase(any())).thenThrow(IllegalArgumentException.class);
        tagService.findByName("testName");
    }

    @Test
    public void findTagByNameTest() {
        String name = "testName";
        Tag outputTag = new Tag();
        outputTag.setName(name);

        when(tagRepository.findByNameIgnoreCase(name)).thenReturn(Optional.of(outputTag));

        Tag resultTag = tagService.findByName(name);

        verify(tagRepository, times(1)).findByNameIgnoreCase(any());

        assertEquals("Names should be the same", name, resultTag.getName());
    }

    @Test
    public void findAllSortedTagsTest() {
        when(tagRepository.findAll((Pageable) any())).thenReturn(null);
        tagService.findAllSorted(null);
        verify(tagRepository, times(1)).findAllSorted((Pageable) any());
    }

    @Test
    public void reviewTagListSetNullTest() {
        Set<Tag> resultSet = tagService.reviewTagList((Set<Tag>) null);

        verify(tagRepository, times(0)).findByNameIgnoreCase(any());

        assertTrue("Return set should be empty", resultSet.isEmpty());
    }

    @Test
    public void reviewTagListSetTest() {
        Tag tag = new Tag();
        Set<Tag> tags = new HashSet<Tag>();
        tags.add(tag);

        when(tagRepository.findByNameIgnoreCase(any())).thenThrow(IllegalArgumentException.class);
        when(tagRepository.saveAndFlush(tag)).thenReturn(tag);

        Set<Tag> resultSet = tagService.reviewTagList(tags);

        verify(tagRepository, times(1)).findByNameIgnoreCase(any());
        verify(tagRepository, times(1)).saveAndFlush(any());

        assertTrue("Result set should have 1 element", resultSet.size() == 1);
    }

    @Test
    public void reviewTagListStringArrayNullTest() {
        Set<Tag> resultSet = tagService.reviewTagList((String[]) null);

        verify(tagRepository, times(0)).findByNameIgnoreCase(any());

        assertTrue("Return set should be empty", resultSet.isEmpty());
    }

    @Test
    public void reviewTagListStringArrayTest() {
        String[] tags = {"first", "second"};

        when(tagRepository.findByNameIgnoreCase(any())).thenThrow(IllegalArgumentException.class);

        Set<Tag> resultSet = tagService.reviewTagList(tags);

        verify(tagRepository, times(tags.length)).findByNameIgnoreCase(any());

        assertTrue("Return set should be empty", resultSet.isEmpty());
    }
}
