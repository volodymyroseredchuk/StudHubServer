package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
