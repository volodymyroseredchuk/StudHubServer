package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TeacherRepository;
import com.softserve.academy.studhub.repository.UniversityRepository;
import com.softserve.academy.studhub.service.impl.TeacherServiceImpl;
import com.softserve.academy.studhub.service.impl.UniversityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UniversityServiceMockTest {
    @Mock
    private UniversityRepository universityRepository;

    private UniversityService universityService;

    private FileService fileService;

    @Before
    public void init() {
        universityService = new UniversityServiceImpl(universityRepository, fileService);
    }


    @Test
    public void saveTest() {
        University newUniversity = new University();
        newUniversity.setName("TestName");
        newUniversity.setCity("TestCity");
        newUniversity.setImageUrl("TestImageUrl");
        newUniversity.setMark(5.0);

        University expectedUniversity = new University();
        expectedUniversity.setName("TestName");
        expectedUniversity.setCity("TestCity");
        expectedUniversity.setImageUrl("TestImageUrl");
        expectedUniversity.setMark(5.0);

        when(universityService.save(newUniversity)).thenReturn(expectedUniversity);

        University resultUniversity = universityService.save(newUniversity);

        assertEquals("Names should be the same", resultUniversity.getName(),
            expectedUniversity.getName());

    }



    @Test
    public void findByIdTest() {
        Integer id = 1;

        University expectedUniversity = new University();
        expectedUniversity.setId(1);

        when(universityRepository.findById(id)).thenReturn(Optional.of(expectedUniversity));

        University resultUniversity = universityService.findById(id);

        assertEquals("IDs should be the same", id, resultUniversity.getId());
        assertNotNull("Name should be not null", resultUniversity.getId());
    }

    @Test(expected = NotFoundException.class)
    public void findByIdNotFoundTest() {
        when(universityRepository.findById(any())).thenThrow(NotFoundException.class);
        universityService.findById(1);
    }

}
