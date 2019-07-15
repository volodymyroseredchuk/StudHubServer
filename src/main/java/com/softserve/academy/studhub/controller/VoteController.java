package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.VotePostDTO;
import com.softserve.academy.studhub.dto.VoteResponseDTO;
import com.softserve.academy.studhub.service.VoteService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@AllArgsConstructor
public class VoteController {

    private VoteService voteService;
    private ModelMapper modelMapper;

    @PostMapping("/votes")
    @PreAuthorize("hasAuthority('VOTE_WRITE_PRIVILEGE')")
    public ResponseEntity<Object> addVote(@Valid @RequestBody VotePostDTO vote,
                                          Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(
                voteService.update(vote, principal.getName()), VoteResponseDTO.class
        ));
    }

    @GetMapping("/votes/question/{questionId}")
    @PreAuthorize("hasAuthority('VOTE_READ_PRIVILEGE')")
    public ResponseEntity<Object> getVotesByUserAndQuestionId(@PathVariable Integer questionId,
                                                              Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(
                voteService.findByUsernameAndQuestionId(principal.getName(), questionId).stream()
                        .map(answer -> modelMapper.map(answer, VoteResponseDTO.class))
                        .collect(Collectors.toList())
        );

    }
}