package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    List<Vote> findByUser(User user);
    List<Vote> findByFeedback(Feedback feedback);
    List<Vote> findByAnswer(Answer answer);
    Optional<Vote> findByUserAndAnswer(User user, Answer answer);
    Optional<Vote> findByUserAndFeedback(User user, Feedback feedback);

    List<Vote> findByUserAndAnswer_Question(User user, Question question);

}
