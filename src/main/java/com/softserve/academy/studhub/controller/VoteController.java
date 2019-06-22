package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.VotePostDTO;
import com.softserve.academy.studhub.dto.message.request.LoginForm;
import com.softserve.academy.studhub.entity.Vote;
import com.softserve.academy.studhub.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@CrossOrigin
@RestController
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/addVote")
    public  ResponseEntity<Object> addVote(@Valid @RequestBody VotePostDTO vote) {

        try{
            return ResponseEntity.status(HttpStatus.OK).body(voteService.update(vote));
        } catch (IllegalArgumentException | NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
