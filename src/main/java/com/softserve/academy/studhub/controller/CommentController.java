package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.service.ICommentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }
    //not sure about urls...
    @GetMapping
    public List<Comment> getAllCommentsForAnswer(Answer answer) {
        return commentService.findByAnswer(answer.getId());
    }

    @PostMapping("/create")
    public Comment createComment(@Valid @RequestBody Comment comment) {

        return commentService.save(comment);
    }

    @PostMapping("/{id}/edit")
    public Comment createComment(@PathVariable Integer id, @RequestBody Comment comment) {

        return commentService.update(id, comment);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteComment (@PathVariable Integer id){
        commentService.deleteById(id);
    }
}
