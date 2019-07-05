package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Proposal;
import com.softserve.academy.studhub.entity.Task;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TaskRepository;
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

    private TaskRepository taskRepository;
    private UserService userService;

    @Override
    public Task save(Task task, Principal principal) {
        task.setCreationDate(LocalDateTime.now());
        task.setUser(userService.findByUsername(principal.getName()));
        return taskRepository.saveAndFlush(task);
    }

    @Override
    public Task update(Integer taskId, Task task) {
        Task updatable = findById(taskId);
        updatable.setTitle(task.getTitle());
        updatable.setBody(task.getBody());
        updatable.setExpectedPrice(task.getExpectedPrice());
        updatable.setDeadlineDate(task.getDeadlineDate());
        updatable.setModifiedDate(LocalDateTime.now());
        return taskRepository.saveAndFlush(updatable);
    }

    @Override
    public Task findById(Integer taskId) {
        return taskRepository.findById(taskId).orElseThrow(
            () -> new NotFoundException(ErrorMessage.TASK_NOT_FOUND_BY_ID + taskId));
    }

    @Override
    public String deleteById(Integer taskId) {
        Task taskToDelete = findById(taskId);
        List<Proposal> proposalList = taskToDelete.getProposalList();
        if ((proposalList == null) || (proposalList.isEmpty())) {
            taskRepository.deleteById(taskId);
            return ErrorMessage.TASK_DELETED;
        }
        return ErrorMessage.TASK_NOT_DELETED;
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAllByOrderByCreationDateDesc(pageable);
    }
}
