package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.CommentRepository;
import com.softserve.academy.studhub.service.AnswerService;
import com.softserve.academy.studhub.service.ICommentService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Integer commentId) {

        return commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException(ErrorMessage.COMMENT_NOTFOUND + commentId));

    }

    @Override
    public String deleteById(Integer commentId) {
        findById(commentId);
        commentRepository.deleteById(commentId);
        return SuccessMessage.COMMENT_DELETED_SUCCESSFULLY;
    }

    @Override
    public List<Comment> findByAnswer(Integer answerId) {
        List<Comment> commentList = null;
        try {
            commentList = commentRepository.findByAnswerIdOrderByCreationDateDesc(answerId);
        } catch (NotFoundException e) {
            e.getMessage();
        }
        return commentList;
    }

}
