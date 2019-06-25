package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Page<Question> findAllByOrderByCreationDateAsc(Pageable pageable);

    Page<Question> findAllDistinctByTagListInOrderByCreationDateDesc(Set<Tag> chosenTags, Pageable pageable);

    @Query(
        value = "select * from Questions q where match(q.title, q.body) against(:pattern) order by :#{#pageable}",
        countQuery = "select count(*) from Questions q where match(q.title, q.body) against(:pattern)",
        nativeQuery = true
    )
    Page<Question> findByFullTextSearch(
        @Param("pattern") String searchPattern,
        @Param("pageable") Pageable pageable);
}
