package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Optional<Feedback> findById(Integer id);

    List<Feedback> findByTeacherId(Integer teacher);

    List<Feedback> findByUniversityId(Integer university);
}
