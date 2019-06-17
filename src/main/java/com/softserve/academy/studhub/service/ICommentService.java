package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Comment;

import java.util.List;

public interface ICommentService {

    Comment save(Comment comment);
    Comment update(Integer commentId, Comment comment);
    List<Comment> findAll();
    Comment findById(Integer commentId);
    List<Comment> findByAnswer(Integer answerId);
    void deleteById(Integer commentId);
    void setQuestionAndAnswer(Integer questionId, Integer answerId, Comment comment);
}
