package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface TaskService {

    Task save(Task task, Principal principal);

    Task update(Integer taskId, Task task);

    Task findById(Integer taskId);

    void deleteById(Integer taskId);

    Page<Task> findAll(Pageable pageable);
}
