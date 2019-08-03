package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    Chat findByName(String name);
    void deleteById(Integer chatId);
}
