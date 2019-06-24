package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    Optional<Subscription> findSubscriptionByChannelIdAndUserId(Integer channelId, Integer userId);

    @Query(value = "select s.user.id from Subscription s where s.channel.question.id = :subjectId")
    List<Integer> findUserIdByChannelQuestionId(@Param ("subjectId") Integer subjectId);

}
