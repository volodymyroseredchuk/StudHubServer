package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
