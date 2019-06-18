package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    Optional<Subscription> findSubscriptionByChannelIdAndUserId(Integer channelId, Integer userId);

}
