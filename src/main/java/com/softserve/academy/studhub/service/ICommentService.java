package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Comment;

import java.util.List;

public interface ICommentService {

    Comment save(Comment comment);
    Comment update(Comment comment);
    List<Comment> findAll();
    Comment findById(int id);
    void deleteById(int id);

}
