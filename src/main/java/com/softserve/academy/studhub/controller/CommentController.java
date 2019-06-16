package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.service.ICommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/questions/{questionId}/answers/{answerId}")
public class CommentController {

    private ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    //not sure about urls... And - do we need this method?
    /*@GetMapping
    public List<Comment> getAllCommentsForAnswer(@PathVariable Integer questionId, @PathVariable Integer answerId) {
        return commentService.findByAnswer(answerId);
    }*/



    @PostMapping("/comments/create")
    @PreAuthorize("hasRole('USER')")
    public Comment createComment(@PathVariable Integer questionId, @PathVariable Integer answerId,
                                 @Valid @RequestBody Comment comment) {
        commentService.setQuestionAndAnswer(questionId, answerId, comment);
        return commentService.save(comment);
    }

    @PutMapping("comments/{commentId}/edit")
    @PreAuthorize("@commentServiceImpl.findById(#commentId).getUser().getUsername() == principal.username")
    public Comment editComment(@PathVariable Integer questionId, @PathVariable Integer answerId,
                               @PathVariable Integer commentId, @Valid @RequestBody Comment comment) {
        commentService.setQuestionAndAnswer(questionId, answerId, comment);
        return commentService.update(commentId, comment);
    }

    @DeleteMapping("comments/{commentId}/delete")
    @PreAuthorize("hasRole('ADMIN') or @commentServiceImpl.findById(#commentId).getUser().getUsername() == principal.username")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {

        commentService.deleteById(commentId);
        return ResponseEntity.ok("Comment deleted");
    }
}
