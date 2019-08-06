package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.CommentRepository;
import com.softserve.academy.studhub.service.AnswerService;
import com.softserve.academy.studhub.service.ICommentService;
import com.softserve.academy.studhub.service.UserService;
import com.sun.security.auth.UserPrincipal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AnswerService answerService;
    @Mock
    private UserService userService;

    private ICommentService commentService;

    @Before
    public void initCommentService() throws Exception {
        commentService = new CommentServiceImpl(commentRepository, answerService, userService);
    }

    @Test
    public void save() {
        Comment comment = new Comment();
        comment.setBody("Test comment");

        User user = new User();
        user.setId(1);
        user.setUsername("testUsername");
        Mockito.when(userService.findByUsername("testUsername")).thenReturn(user);

        Answer answer = new Answer();
        answer.setId(1);
        Mockito.when(answerService.findById(1)).thenReturn(answer);

        Comment commentResponse = new Comment(1,
                "Test body",
                LocalDateTime.now(),
                LocalDateTime.now(),
                answer,
                user
        );
        Mockito.when(commentRepository.saveAndFlush(Mockito.any())).thenReturn(commentResponse);
        Principal principal = new UserPrincipal(user.getUsername());

        Comment returnedComment = commentService.save(1, comment, principal);
        Assert.assertEquals(returnedComment, commentResponse);

    }

    @Test(expected = NullPointerException.class)
    public void saveWithIncorrectUser() {
        Comment comment = new Comment();
        comment.setBody("Test comment");

        Answer answer = new Answer();
        answer.setId(1);
        Mockito.when(answerService.findById(1)).thenReturn(answer);

        Comment commentResponse = new Comment(1,
                "Test body",
                LocalDateTime.now(),
                LocalDateTime.now(),
                answer,
                null
        );
        Mockito.when(commentRepository.saveAndFlush(Mockito.any())).thenReturn(commentResponse);
        Principal principal = new UserPrincipal(null);

        commentService.save(1, comment, principal);
    }

    @Test
    public void findAll() {
        List<Comment> commentList = new ArrayList<>();
        Mockito.when(commentRepository.findAll()).thenReturn(commentList);
        Assert.assertEquals(commentService.findAll(), commentList);
    }

    @Test
    public void findById() {

        Comment comment = new Comment();
        comment.setId(1);
        Mockito.when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        Assert.assertEquals(commentService.findById(1), comment);
    }

    @Test(expected = NotFoundException.class)
    public void findByIncorrectId() {

        Mockito.when(commentRepository.findById(1)).thenReturn(Optional.empty());
        commentService.findById(1);
    }

    @Test
    public void deleteById() {
        Comment commentToDelete = new Comment();
        Mockito.when(commentRepository.findById(1)).thenReturn(Optional.of(commentToDelete));
        Assert.assertEquals("Comment deleted", commentService.deleteById(1));
        Mockito.verify(commentRepository).deleteById(1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteByIncorrectId() {
        commentService.deleteById(Mockito.anyInt());
    }

    @Test
    public void findByAnswer() {
        List<Comment> commentList = new ArrayList<>();
        Mockito.when(commentRepository.findByAnswerIdOrderByCreationDateDesc(1)).thenReturn(commentList);
        Assert.assertEquals(commentService.findByAnswer(1), commentList);
    }
}