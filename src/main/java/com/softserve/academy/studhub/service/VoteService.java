package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.VotePostDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Feedback;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.Vote;

import java.util.List;

public interface VoteService {

    Vote findById(Integer id);
    List<Vote> findByUser(User user);
    List<Vote> findByAnswer(Answer answer);
    List<Vote> findByFeedback(Feedback feedback);
    List<Vote> findAll();

    List<Vote> findByUsernameAndQuestionId(String username, Integer questionId);
    Vote save(Vote vote);
    Vote update(VotePostDTO votePostDTO, String username);
    void delete(Vote vote);

}
