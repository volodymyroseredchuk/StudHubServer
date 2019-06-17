package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.AnswerCreateDTO;
import com.softserve.academy.studhub.dto.AnswerDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.security.jwt.JwtProvider;
import com.softserve.academy.studhub.service.AnswerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
public class AnswerController {

    private AnswerService answerService;

    private ModelMapper modelMapper;

    private JwtProvider jwtProvider;

    @GetMapping("/question/{questionId}/answer")
    public ResponseEntity<List<AnswerDTO>> getAnswersByQuestionId(@PathVariable Integer questionId){
        List<Answer> answers = answerService.findAllByQuestionId(questionId);
        List<AnswerDTO> answerDTOS = answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(answerDTOS);

    }

    @PostMapping("/question/{questionId}/answer")
    public ResponseEntity<?> createAnswer(@Valid @RequestBody AnswerCreateDTO answerCreateDTO,
                                  @PathVariable Integer questionId,
                                  @RequestHeader("Bearer") String token){

        String username = jwtProvider.getUserNameFromJwtToken(token);

        return ResponseEntity.ok(modelMapper.map(
                answerService.save(answerCreateDTO, questionId, username), AnswerDTO.class)
        );
    }


    @DeleteMapping("/question/{questionId}/answer/{answerId}/delete")
    //@PreAuthorize("hasRole('ADMIN') or @answerServiceImpl.findById(#answerId).getUser().getUsername() == authentication.getName()")
    public ResponseEntity<String> deleteAnswer(@PathVariable Integer answerId){

        answerService.deleteById(answerId);

        return ResponseEntity.ok("Answer deleted");
    }
}
