package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.FeedbackDTO;
import com.softserve.academy.studhub.entity.Feedback;
import com.softserve.academy.studhub.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    private final ModelMapper modelMapper;

    public FeedbackController(FeedbackService feedbackService, ModelMapper modelMapper) {
        this.feedbackService = feedbackService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<Feedback> getAllFeedbacks() {

        return feedbackService.findAll();
    }

    @GetMapping(path = "/teacher/{teacherId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByTeacher(@PathVariable Integer teacherId) {
        List<Feedback> feedbacks = feedbackService.findByTeacherId(teacherId);
        List<FeedbackDTO> feedbackDTOS = feedbacks.stream()
            .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
            .collect(Collectors.toList());
        return ResponseEntity.ok(feedbackDTOS);
    }

    @PostMapping(path = "/feedback")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FeedbackDTO> addNewFeedback(@RequestBody FeedbackDTO feedbackDTO) {

        Feedback result = feedbackService.save(modelMapper.map(feedbackDTO, Feedback.class));
        return ResponseEntity.ok(modelMapper.map(result, FeedbackDTO.class));
    }

    @GetMapping(path = "/university/{universityId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByUniversuty(@PathVariable Integer universityId) {
        List<Feedback> feedbacks = feedbackService.findByUniversityId(universityId);
        List<FeedbackDTO> feedbackDTOS = feedbacks.stream()
            .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok(feedbackDTOS);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacksByCurrentUser(@PathVariable String username) {

        return ResponseEntity.ok(feedbackService.findFeedbackByUserUsername(username).
                stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).
                collect(Collectors.toList()));
    }

}
