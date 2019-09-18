package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TeacherRepository;
import com.softserve.academy.studhub.service.impl.TeacherServiceImpl;
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
public class TeacherServiceMockTest {
    @Mock
    private TeacherRepository teacherRepository;

    private TeacherService teacherService;

    private FileService fileService;

    @Before
    public void init() {
        teacherService = new TeacherServiceImpl(teacherRepository, fileService);
    }


    @Test
    public void saveTest() {
        Teacher newTeacher = new Teacher();
        newTeacher.setFirstName("TestFirstName");
        newTeacher.setLastName("TestLastName");
        newTeacher.setImageUrl("Image");
        newTeacher.setMark(5.0);

        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setFirstName("TestFirstName");
        expectedTeacher.setLastName("TestLastName");
        expectedTeacher.setImageUrl("Image");
        expectedTeacher.setMark(5.0);

        when(teacherService.save(newTeacher)).thenReturn(expectedTeacher);

        Teacher resultTeacher = teacherService.save(newTeacher);

        assertEquals("Names should be the same", resultTeacher.getLastName(),
            expectedTeacher.getLastName());

    }

    @Test
    public void updateTest() {
        Teacher newTeacher = new Teacher();
        newTeacher.setId(1);
        newTeacher.setFirstName("TestFirstName");
        newTeacher.setLastName("TestLastName");
        newTeacher.setImageUrl("Image");
        newTeacher.setMark(5.0);

        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setId(1);
        expectedTeacher.setFirstName("TestFirstName");
        expectedTeacher.setLastName("TestLastName");
        expectedTeacher.setImageUrl("Image");
        expectedTeacher.setMark(5.0);


        when(teacherRepository.saveAndFlush(newTeacher)).thenReturn(expectedTeacher);
        when(teacherRepository.findById(1)).thenReturn(Optional.of(newTeacher));

        Teacher resultTeacher = teacherService.update(newTeacher);

        assertNotEquals("New id should not be 0", Integer.valueOf(0), resultTeacher.getId());
        assertEquals("Id should be the same", resultTeacher.getId(),
            expectedTeacher.getId());

    }

    @Test
    public void findByIdTest() {
        Integer id = 1;

        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setId(1);

        when(teacherRepository.findById(id)).thenReturn(Optional.of(expectedTeacher));

        Teacher resultTeacher = teacherService.findById(id);

        assertEquals("IDs should be the same", id, resultTeacher.getId());
        assertNotNull("Last Name should be not null", resultTeacher.getId());
    }

    @Test(expected = NotFoundException.class)
    public void findByIdNotFoundTest() {
        when(teacherRepository.findById(any())).thenThrow(NotFoundException.class);
        teacherService.findById(1);
    }

}
