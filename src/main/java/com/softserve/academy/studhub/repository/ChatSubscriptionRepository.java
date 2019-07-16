package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.ChatSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatSubscriptionRepository extends JpaRepository<ChatSubscription, Integer> {

    List<ChatSubscription> findChatSubscriptionByChatId(Integer chatId);
    List<ChatSubscription> findChatSubscriptionByUserId(Integer userId);

}
