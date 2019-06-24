package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.exceptions.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.AnswerRepository;
import com.softserve.academy.studhub.repository.CommentRepository;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.AnswerService;
import com.softserve.academy.studhub.service.ICommentService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    private CommentRepository commentRepository;
    private AnswerService answerService;
    private UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              AnswerService answerService,
                              UserService userService) {
        this.commentRepository = commentRepository;
        this.answerService = answerService;
        this.userService = userService;
    }

    @Override
    public Comment save(Integer answerId, Comment comment, Principal principal) {
        comment.setCreationDate(LocalDateTime.now());
        comment.setAnswer(answerService.findById(answerId));
        comment.setUser(userService.findByUsername(principal.getName()));
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public Comment update(Integer commentId, Comment comment) {
        Comment updatable = findById(commentId);
        updatable.setBody(comment.getBody());
        updatable.setModifiedDate(LocalDateTime.now());
        return commentRepository.saveAndFlush(updatable);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Integer commentId) {

        return commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException(ErrorMessage.COMMENT_NOTFOUND + commentId));

    }

    @Override
    public void deleteById(Integer commentId) {
        findById(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> findByAnswer(Integer answerId) throws IllegalArgumentException {
        List<Comment> commentList = null;
        try {
            commentList = commentRepository.findAllByAnswer_Id(answerId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return commentList;
    }

}
