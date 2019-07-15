package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.AnswerCreateDTO;
import com.softserve.academy.studhub.dto.AnswerDTO;
import com.softserve.academy.studhub.dto.AnswerApproveDTO;
import com.softserve.academy.studhub.dto.DeleteResultDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.service.AnswerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
public class AnswerController {

    private AnswerService answerService;

    private ModelMapper modelMapper;


    @GetMapping("/questions/{questionId}/answers")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<AnswerDTO>> getAnswersByQuestionId(@PathVariable Integer questionId) {
        List<Answer> answers = answerService.findAllByQuestionId(questionId);
        List<AnswerDTO> answerDTOS = answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(answerDTOS);

    }

    @PostMapping("/questions/{questionId}/answers")
    @PreAuthorize("hasAuthority('QUESTION_WRITE_PRIVILEGE')")
    public ResponseEntity<AnswerDTO> createAnswer(@Valid @RequestBody AnswerCreateDTO answerCreateDTO,
                                                  @PathVariable Integer questionId,
                                                  Principal principal) {
        return ResponseEntity.ok(modelMapper.map(
                answerService.save(answerCreateDTO, questionId, principal.getName()), AnswerDTO.class)
        );
    }


    @DeleteMapping("/questions/{questionId}/answers/{answerId}/delete")
    @PreAuthorize("hasAuthority('QUESTION_DELETE_ANY_PRIVILEGE') or @answerServiceImpl.findById(#answerId).getUser().getUsername() == principal.username")
    public ResponseEntity<DeleteResultDTO> deleteAnswer(@PathVariable Integer answerId) {
        Boolean isDeleted = answerService.deleteById(answerId);
        DeleteResultDTO deleteResultDTO = new DeleteResultDTO();
        deleteResultDTO.setIsDeleted(isDeleted);
        return ResponseEntity.ok(deleteResultDTO);
    }


    @PutMapping("/questions/{questionId}/answers/{answerId}/approve")
    @PreAuthorize("hasAuthority('QUESTION_WRITE_PRIVILEGE') and @answerServiceImpl.findById(#answerId).getQuestion().getUser().getUsername() == principal.username")
    public ResponseEntity<AnswerApproveDTO> setApprovedAnswer(@PathVariable Integer answerId,
                                                              @RequestBody Boolean approved) {
        AnswerApproveDTO answerApproveDTO = new AnswerApproveDTO();
        answerApproveDTO.setAnswerId(answerId);
        answerApproveDTO.setApproved(approved);
        return ResponseEntity.ok(answerService.approve(answerApproveDTO));
    }

}
