package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.*;
import com.softserve.academy.studhub.entity.Task;
import com.softserve.academy.studhub.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<TaskPaginatedDTO> getAllTasks(Pageable pageable) {
        Page<Task> taskPage = taskService.findAll(pageable);

        List<TaskForListDTO> taskForListDTOs = taskPage.getContent().stream()
            .map(task -> modelMapper.map(task, TaskForListDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new TaskPaginatedDTO(taskForListDTOs, taskPage.getTotalElements()));
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Integer taskId) {
        Task task = taskService.findById(taskId);

        return ResponseEntity.ok().body(modelMapper.map(task, TaskDTO.class));
    }
}
