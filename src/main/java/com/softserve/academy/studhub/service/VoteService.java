package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.VotePostDTO;
import com.softserve.academy.studhub.entity.Vote;

import java.util.List;

public interface VoteService {

    List<Vote> findByUsernameAndQuestionId(String username, Integer questionId);
    Vote update(VotePostDTO votePostDTO, String username);

    Integer getVoteSumByUsername(String username);
}
