package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Feedback;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.Vote;
import com.softserve.academy.studhub.repository.VoteRepository;
import com.softserve.academy.studhub.service.VoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Vote findById(Integer id) {
        Optional<Vote> resultVote = voteRepository.findById(id);
        if (resultVote.isPresent()) {
            return resultVote.get();
        } else {
            throw new IllegalArgumentException("Vote not found.");
        }
    }

    @Override
    public List<Vote> findByUser(User user) {
        return voteRepository.findByUser(user);
    }

    @Override
    public List<Vote> findByAnswer(Answer answer) {
        return voteRepository.findByAnswer(answer);
    }

    @Override
    public List<Vote> findByFeedback(Feedback feedback) {
        return voteRepository.findByFeedback(feedback);
    }

    @Override
    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    @Override
    public Vote save(Vote vote) {
        return voteRepository.saveAndFlush(vote);
    }

    @Override
    public Vote update(Vote vote) {
        return voteRepository.saveAndFlush(vote);
    }

    @Override
    public void delete(Vote vote) {
        voteRepository.delete(vote);
    }

}
