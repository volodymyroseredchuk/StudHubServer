package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Comment;

import java.security.Principal;
import java.util.List;

public interface ICommentService {

    Comment save(Integer answerId, Comment comment, Principal principal);

    List<Comment> findAll();

    Comment findById(Integer commentId);

    List<Comment> findByAnswer(Integer answerId);

    String deleteById(Integer commentId);

}
