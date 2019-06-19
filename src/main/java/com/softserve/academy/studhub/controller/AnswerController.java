package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.AnswerCreateDTO;
import com.softserve.academy.studhub.dto.AnswerDTO;
import com.softserve.academy.studhub.dto.AnswerApproveDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.security.jwt.JwtProvider;
import com.softserve.academy.studhub.service.AnswerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<AnswerDTO>> getAnswersByQuestionId(@PathVariable Integer questionId){
        List<Answer> answers = answerService.findAllByQuestionId(questionId);
        List<AnswerDTO> answerDTOS = answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(answerDTOS);

    }

    @PostMapping("/questions/{questionId}/answers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createAnswer(@Valid @RequestBody AnswerCreateDTO answerCreateDTO,
                                          @PathVariable Integer questionId,
                                          Principal principal){

        String username = principal.getName();

        return ResponseEntity.ok(modelMapper.map(
                answerService.save(answerCreateDTO, questionId, username), AnswerDTO.class)
        );
    }


    @DeleteMapping("/questions/{questionId}/answers/{answerId}/delete")
    @PreAuthorize("hasRole('ADMIN') or @answerServiceImpl.findById(#answerId).getUser().getUsername() == principal.username")
    public ResponseEntity<String> deleteAnswer(@PathVariable Integer answerId){

        answerService.deleteById(answerId);

        return ResponseEntity.ok("Answer deleted");
    }


    @PutMapping("/questions/{questionId}/answers/{answerId}/approve")
    @PreAuthorize("@answerServiceImpl.findById(#answerId).getQuestion().getUser().getUsername() == principal.username")
    public ResponseEntity<?> setApprovedAnswer (@PathVariable Integer answerId,
                                                @RequestBody Boolean approved) {
        AnswerApproveDTO answerApproveDTO = new AnswerApproveDTO();
        answerApproveDTO.setAnswerId(answerId);
        answerApproveDTO.setApproved(approved);
        try {
            return ResponseEntity.ok(answerService.approve(answerApproveDTO));
        } catch(IllegalArgumentException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
