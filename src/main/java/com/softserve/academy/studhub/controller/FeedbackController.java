package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.FeedbackDTO;
import com.softserve.academy.studhub.entity.Feedback;
import com.softserve.academy.studhub.service.FeedbackService;
import com.softserve.academy.studhub.service.TeacherService;
import com.softserve.academy.studhub.service.UniversityService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(path = "/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final TeacherService teacherService;
    private final UniversityService universityService;
    private final UserService userService;

    private final ModelMapper modelMapper;

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

    @PostMapping
    @PreAuthorize("hasAuthority('FEEDBACK_WRITE_PRIVILEGE')")
    public ResponseEntity<FeedbackDTO> addNewFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        Feedback newFeedback = new Feedback();
        newFeedback.setBody(feedbackDTO.getBody());
        newFeedback.setMark(feedbackDTO.getMark());

        if (Objects.nonNull(feedbackDTO.getTeacherId())) {
            newFeedback.setTeacher(teacherService.findById(feedbackDTO.getTeacherId()));
        }

        if (Objects.nonNull(feedbackDTO.getUniversityId())) {
            newFeedback.setUniversity(universityService.findById(feedbackDTO.getUniversityId()));
        }
        newFeedback.setRate(0);
        Feedback result = feedbackService.save(newFeedback);
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
