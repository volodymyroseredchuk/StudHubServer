package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);

    @Query("select t from Tag t order by t.questionList.size desc")
    Page<Tag> findAllSorted(Pageable pageable);
}
