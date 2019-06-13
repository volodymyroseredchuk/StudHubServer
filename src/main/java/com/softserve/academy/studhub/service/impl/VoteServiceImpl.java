package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.dto.VotePostDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Feedback;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.Vote;
import com.softserve.academy.studhub.repository.AnswerRepository;
import com.softserve.academy.studhub.repository.FeedbackRepository;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.repository.VoteRepository;
import com.softserve.academy.studhub.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

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
    public Vote update(VotePostDTO voteDTO) throws NullPointerException, IllegalArgumentException {
        Optional<User> userResponse = userRepository.findById(voteDTO.getUserId());
        if (userResponse.isPresent()) {
            User user = userResponse.get();
            if (voteDTO.getAnswerId() != null) {
                Optional<Answer> answerResponse = answerRepository.findById(voteDTO.getAnswerId());
                if (answerResponse.isPresent()) {
                    Answer answer = answerResponse.get();

                    Optional<Vote> voteResponse = voteRepository
                            .findByUserAndAnswer(user, answer);
                    if (voteResponse.isPresent()) {
                        Vote dbVote = voteResponse.get();
                        dbVote.setValue(voteDTO.getValue());
                        return voteRepository.saveAndFlush(dbVote);
                    } else {
                        Vote vote = new Vote();
                        vote.setValue(voteDTO.getValue());
                        vote.setAnswer(answer);
                        vote.setUser(user);
                        return this.save(vote);
                    }

                } else {
                    throw new NullPointerException("Answer does not exist.");
                }

            } else if (voteDTO.getFeedbackId() != null) {

                Optional<Feedback> feedbackResponse = feedbackRepository.findById(voteDTO.getFeedbackId());
                if (feedbackResponse.isPresent()) {
                    Feedback feedback = feedbackResponse.get();

                    Optional<Vote> voteResponse = voteRepository.findByUserAndFeedback(user, feedback);
                    if (voteResponse.isPresent()) {
                        Vote dbVote = voteResponse.get();
                        dbVote.setValue(voteDTO.getValue());
                        return voteRepository.saveAndFlush(dbVote);
                    } else {
                        Vote vote = new Vote();
                        vote.setUser(user);
                        vote.setFeedback(feedback);
                        vote.setValue(voteDTO.getValue());
                        return this.save(vote);
                    }

                } else {
                    throw new NullPointerException("Feedback does not exist.");
                }
            } else {
                throw new IllegalArgumentException("Got invalid vote.");
            }
        } else {
            throw new NullPointerException("User does not exist.");
        }
    }

    @Override
    public void delete(Vote vote) {
        voteRepository.delete(vote);
    }


}
