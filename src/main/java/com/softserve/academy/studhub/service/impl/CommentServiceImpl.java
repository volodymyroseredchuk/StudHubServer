package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.exceptions.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.AnswerRepository;
import com.softserve.academy.studhub.repository.CommentRepository;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.ICommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    private CommentRepository commentRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              QuestionRepository questionRepository,
                              AnswerRepository answerRepository) {
        this.commentRepository = commentRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Comment save(Comment comment) {
        comment.setCreationDate(LocalDateTime.now());
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

    @Override
    public void setQuestionAndAnswer(Integer questionId, Integer answerId,
                                     Comment comment) {
        Question question = null;
        Answer answer = null;
        Optional<Question> q = questionRepository.findById(questionId);
        Optional<Answer> a = answerRepository.findById(answerId);

        if (q.isPresent()) {
            question = q.get();
        }
        if (a.isPresent()) {
            answer = a.get();
            answer.setQuestion(question);
        }
        comment.setAnswer(answer);
    }
}
