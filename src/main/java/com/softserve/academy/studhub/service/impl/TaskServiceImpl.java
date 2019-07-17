package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Proposal;
import com.softserve.academy.studhub.entity.Task;
import com.softserve.academy.studhub.entity.enums.TaskStatus;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TaskRepository;
import com.softserve.academy.studhub.service.TagService;
import com.softserve.academy.studhub.service.TaskService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TagService tagService;

    @Override
    public Task save(Task task, Principal principal) {

        task.setCreationDate(LocalDateTime.now());
        task.setUser(userService.findByUsername(principal.getName()));
        task.setStatus(TaskStatus.NEW);
        task.setTagList(tagService.reviewTagList(task.getTagList()));

        return taskRepository.saveAndFlush(task);
    }

    @Override
    public Task update(Integer taskId, Task task) {

        Task updatable = findById(taskId);
        updatable.setTitle(task.getTitle());
        updatable.setBody(task.getBody());
        updatable.setExpectedPrice(task.getExpectedPrice());
        updatable.setDeadlineDate(task.getDeadlineDate());
        updatable.setTagList(task.getTagList());
        updatable.setModifiedDate(LocalDateTime.now());

        return taskRepository.saveAndFlush(updatable);
    }

    @Override
    public Task findById(Integer taskId) {

        return taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.TASK_NOT_FOUND_BY_ID + taskId));
    }

    @Override
    public void deleteById(Integer taskId) {

        taskRepository.deleteById(taskId);
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {

        return taskRepository.findAllByStatusOrderByCreationDateDesc(TaskStatus.NEW, pageable);
    }

    @Override
    public Page<Task> searchByTags(String[] tags, Pageable pageable) {

        return taskRepository.findAllDistinctByTagListInOrderByCreationDateDesc(tagService.reviewTagList(tags), pageable);
    }

    @Override
    public Page<Task> searchByKeywords(String[] keywords, Pageable pageable) {

        return taskRepository.findByFullTextSearch(String.join(" ", keywords), pageable);
    }
}
