package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.dto.DeleteDTO;
import com.softserve.academy.studhub.dto.QuestionDTO;
import com.softserve.academy.studhub.dto.QuestionForListDTO;
import com.softserve.academy.studhub.dto.QuestionPaginatedDTO;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.security.dto.MessageResponse;
import com.softserve.academy.studhub.service.IQuestionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/teams/{teamId}/questions")
public class TeamQuestionController {

    private final IQuestionService questionService;
    private final ModelMapper modelMapper;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
            "(isAuthenticated() and @teamServiceImpl.hasAccessForUser(#teamId, principal.username))")
    public ResponseEntity<QuestionPaginatedDTO> getAllQuestionsByTeamId(@PathVariable Integer teamId, Pageable pageable) {

        Page<Question> questionPage = questionService.findAllByTeamId(teamId, pageable);

        List<QuestionForListDTO> questionDTOS = questionPage.getContent().stream()
                .map(question -> modelMapper.map(question, QuestionForListDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionDTOS, questionPage.getTotalElements()));
    }

    @GetMapping("/{questionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
            "(isAuthenticated() and @teamServiceImpl.hasAccessForUser(#teamId, principal.username))")
    public ResponseEntity<QuestionDTO> getTeamQuestionById(@PathVariable Integer teamId, @PathVariable Integer questionId) {

        Question result = questionService.findById(questionId);
        return ResponseEntity.ok(modelMapper.map(result, QuestionDTO.class));
    }

    @GetMapping("/search/{keywords}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
            "(isAuthenticated() and @teamServiceImpl.hasAccessForUser(#teamId, principal.username))")
    public ResponseEntity<QuestionPaginatedDTO> getTeamSearchedByKeywordsQuestions(@PathVariable Integer teamId, @PathVariable String[] keywords, Pageable pageable) {

        Page<Question> questionPage = questionService.searchByKeywords(keywords, pageable);

        List<QuestionForListDTO> questionForListDTOs = questionPage.getContent().stream()
                .map(question -> modelMapper.map(question, QuestionForListDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionForListDTOs, questionPage.getTotalElements()));
    }


    @GetMapping("/tagged/{tags}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
            "(isAuthenticated() and @teamServiceImpl.hasAccessForUser(#teamId, principal.username))")
    public ResponseEntity<QuestionPaginatedDTO> getTeamSearchedByTagsQuestions(@PathVariable Integer teamId, @PathVariable String[] tags, Pageable pageable) {

        Page<Question> questionPage = questionService.searchByTags(tags, pageable);

        List<QuestionForListDTO> questionForListDTOs = questionPage.getContent().stream()
                .map(question -> modelMapper.map(question, QuestionForListDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionForListDTOs, questionPage.getTotalElements()));
    }

    @PostMapping("/create")
    @PreAuthorize("(isAuthenticated() and @teamServiceImpl.hasAccessForUser(#teamId, principal.username))")
    public ResponseEntity<QuestionDTO> createTeamQuestion(@PathVariable Integer teamId, @RequestBody QuestionDTO questionDto, Principal principal) {

        Question result = questionService.save(modelMapper.map(questionDto, Question.class), principal);
        return ResponseEntity.ok(modelMapper.map(result, QuestionDTO.class));
    }

    @PutMapping("/{questionId}")
    @PreAuthorize("isAuthenticated() and @questionServiceImpl.findById(#questionId).getUser().getUsername() == principal.username")
    public ResponseEntity<QuestionDTO> editTeamQuestion(@PathVariable Integer teamId, @PathVariable Integer questionId, @RequestBody QuestionDTO questionDto) {

        Question result = questionService.update(questionId, modelMapper.map(questionDto, Question.class));
        return ResponseEntity.ok(modelMapper.map(result, QuestionDTO.class));
    }

    @DeleteMapping("/{questionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
            "isAuthenticated() and @teamServiceImpl.findById(#teamId).getUser().getUsername() == principal.username or " +
            "isAuthenticated() and @questionServiceImpl.findById(#questionId)" +
            ".getUser().getUsername() == principal.username")
    public ResponseEntity<MessageResponse> deleteTeamQuestion(@PathVariable Integer teamId,
                                                              @PathVariable Integer questionId) {

        questionService.deleteById(questionId);
        return ResponseEntity.ok().body(new MessageResponse(SuccessMessage.QUESTION_DELETED_SUCCESSFULLY));
    }
}