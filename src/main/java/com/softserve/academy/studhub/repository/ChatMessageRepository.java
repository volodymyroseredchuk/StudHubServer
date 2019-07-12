package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    Page<ChatMessage> findByChatIdOrderByCreationDateTimeDesc(Integer chatId, Pageable pageable);
    Optional<ChatMessage> findFirstChatMessageByChatIdOrderByCreationDateTimeDesc(Integer chatId);

}
