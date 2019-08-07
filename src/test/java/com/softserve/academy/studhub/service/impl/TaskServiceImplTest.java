package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Task;
import com.softserve.academy.studhub.entity.enums.TaskStatus;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TaskRepository;
import com.softserve.academy.studhub.service.TagService;
import com.softserve.academy.studhub.service.TaskService;
import com.softserve.academy.studhub.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private TagService tagService;

    private TaskService taskService;

    @Before
    public void init() {
        taskService = new TaskServiceImpl(taskRepository, userService, tagService);
    }

    @Test
    public void save() {

        Task task = new Task();
        task.setStatus(TaskStatus.NEW);

        Principal principal = () -> "Jarvizz";

        when(taskRepository.saveAndFlush(task)).thenReturn(task);

        Task resultTask = taskService.save(task, principal);

        Assert.assertEquals(resultTask.getStatus(), task.getStatus());
    }

    @Test
    public void update() {

        Task task = new Task();
        task.setTitle("task");

        Task updatedTask = new Task();
        task.setTitle("updatedTask");

        when(taskRepository.saveAndFlush(task)).thenReturn(updatedTask);
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        Task resultTask = taskService.update(1, task);

        Assert.assertEquals(updatedTask.getTitle(), resultTask.getTitle());
    }

    @Test
    public void findById() {

        Integer teamId = 1;

        Task task = new Task();
        task.setId(teamId);

        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        Assert.assertEquals(task, taskService.findById(teamId));
    }

    @Test(expected = NotFoundException.class)
    public void findByInvalidId() {
        taskService.findById(null);
    }

}