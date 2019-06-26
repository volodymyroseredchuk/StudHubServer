package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.CommentDTO;
import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.service.ICommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/questions/{questionId}/answers/{answerId}")
public class CommentController {

    private ICommentService commentService;
    private ModelMapper modelMapper;


    @PostMapping("/comments/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Integer answerId,
                                                    @Valid @RequestBody CommentDTO commentDTO, Principal principal) {

        Comment result = commentService.save(answerId, modelMapper.map(commentDTO, Comment.class), principal);
        return ResponseEntity.ok(modelMapper.map(result, CommentDTO.class));

    }

    @PutMapping("comments/{commentId}")
    @PreAuthorize("isAuthenticated() and @commentServiceImpl.findById(#commentId).getUser().getUsername() == principal.username")
    public ResponseEntity<CommentDTO> editComment(@PathVariable Integer commentId, @Valid @RequestBody CommentDTO commentDTO) {
        Comment result = commentService.update(commentId, modelMapper.map(commentDTO, Comment.class));
        return ResponseEntity.ok(modelMapper.map(result, CommentDTO.class));

    }

    @DeleteMapping("comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentServiceImpl.findById(#commentId).getUser().getUsername() == principal.username")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {

        commentService.deleteById(commentId);
        return ResponseEntity.ok("Comment deleted");
    }
}
