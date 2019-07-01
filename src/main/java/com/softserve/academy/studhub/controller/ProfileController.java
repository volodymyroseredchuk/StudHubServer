package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.FeedbackDTO;
import com.softserve.academy.studhub.dto.QuestionForListDTO;
import com.softserve.academy.studhub.dto.UserDTO;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.service.FeedbackService;
import com.softserve.academy.studhub.service.IQuestionService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    private final IQuestionService questionService;

    private final FeedbackService feedbackService;

    private final ModelMapper modelMapper;

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> gerCurrentUser(Principal principal) {

        String username = principal.getName();

        return new ResponseEntity<>(modelMapper.
                map(userService.findByUsername(username), UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/foreign/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserDTO> getForeignUser(@PathVariable Integer id) {

        return new ResponseEntity<>(modelMapper.
                map(userService.findById(id), UserDTO.class), HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {

        return new ResponseEntity<>(modelMapper.
                map(userService.update(user), UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/questions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<QuestionForListDTO>> getAllQuestionsByCurrentUser(Principal principal) {

        return new ResponseEntity<>(questionService.
                findQuestionByUserUsernameOrderByCreationDateDesc(principal.getName()).
                stream().map(question -> modelMapper.map(question, QuestionForListDTO.class)).
                collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/feedbacks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacksByCurrentUser(Principal principal) {

        return new ResponseEntity<>(feedbackService.
                findFeedbackByUserUsernameOrderByCreationDateDesc(principal.getName()).
                stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).
                collect(Collectors.toList()), HttpStatus.OK);
    }

}
