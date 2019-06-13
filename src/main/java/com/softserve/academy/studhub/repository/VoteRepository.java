package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Feedback;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    List<Vote> findByUser(User user);
    List<Vote> findByFeedback(Feedback feedback);
    List<Vote> findByAnswer(Answer answer);
    Optional<Vote> findByUserAndAndAnswer(User user, Answer answer);
    Optional<Vote> findByUserAndFeedback(User user, Feedback feedback);

}
