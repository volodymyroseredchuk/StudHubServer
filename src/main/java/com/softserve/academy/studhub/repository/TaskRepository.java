package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Page<Task> findAllByOrderByCreationDateDesc(Pageable pageable);

    Page<Task> findAllDistinctByTagListInOrderByCreationDateDesc(Set<Tag> chosenTags, Pageable pageable);

    @Query(
            value = "select * from Tasks t where match(t.title, t.body) against(:pattern) order by :#{#pageable}",
            countQuery = "select count(*) from Tasks t where match(t.title, t.body) against(:pattern)",
            nativeQuery = true
    )
    Page<Task> findByFullTextSearch(@Param("pattern") String searchPattern,
            @Param("pageable") Pageable pageable);
}
