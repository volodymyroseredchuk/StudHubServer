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

import javax.validation.Valid;
import java.security.Principal;
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

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO, Principal principal) {

        Task task = taskService.save(modelMapper.map(taskDTO, Task.class), principal);

        return ResponseEntity.ok().body(modelMapper.map(task, TaskDTO.class));
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("isAuthenticated() and @taskServiceImpl.findById(#taskId).getUser().getUsername() == principal.username")
    public ResponseEntity<TaskDTO> editTask(@PathVariable Integer taskId, @RequestBody TaskDTO taskDTO) {

        Task task = taskService.update(taskId, modelMapper.map(taskDTO, Task.class));

        return ResponseEntity.ok().body(modelMapper.map(task, TaskDTO.class));
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
        "@taskServiceImpl.findById(#taskId).getUser().getUsername()== principal.username")
    public ResponseEntity<DeleteDTO> deleteTask(@PathVariable Integer taskId) {

        return ResponseEntity.ok().body(new DeleteDTO(taskService.deleteById(taskId)));

    }
}
