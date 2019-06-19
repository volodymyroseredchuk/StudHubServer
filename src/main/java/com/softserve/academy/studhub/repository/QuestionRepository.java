package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findAllByOrderByCreationDateAsc();

    List<Question> findAllByTagListInOrderByCreationDateAsc(List<Tag> chosenTags);

    @Query(
        value = "select * from Questions q where match(q.title, q.body) against(?1) order by ?#{#pageable}",
        countQuery = "select count(*) from Questions q where match(q.title, q.body) against(?1)",
        nativeQuery = true
    )
    Page<Question> findByFullTextSearch(
        String searchPattern,
        Pageable pageable);
}
