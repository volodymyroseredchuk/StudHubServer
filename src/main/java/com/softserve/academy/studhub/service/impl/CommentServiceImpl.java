package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.dao.CommentRepository;
import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    private CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment save(Comment comment) {
        return repository.saveAndFlush(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return repository.saveAndFlush(comment);
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public Comment findById(int id) {
        Optional<Comment> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new IllegalArgumentException("Requested comment does not exist");
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
