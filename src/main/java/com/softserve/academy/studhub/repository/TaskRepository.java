package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Page<Task> findAllByOrderByCreationDateDesc(Pageable pageable);
}
