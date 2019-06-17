package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByAnswer_Id(Integer id);


}
