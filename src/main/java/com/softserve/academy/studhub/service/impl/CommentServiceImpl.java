package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.repository.CommentRepository;
import com.softserve.academy.studhub.service.ICommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    private CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment save(Comment comment) {
        comment.setCreationDate(LocalDateTime.now());
        return repository.saveAndFlush(comment);
    }

    @Override
    public Comment update(int id, Comment comment) {
        Comment updatable = findById(id);
        updatable.setBody(comment.getBody());
        updatable.setModifiedDate(LocalDateTime.now());
        return repository.saveAndFlush(updatable);
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

    @Override
    public List<Comment> findByAnswer(Integer id) {
        return repository.findAllByAnswer_Id(id);
    }
}
