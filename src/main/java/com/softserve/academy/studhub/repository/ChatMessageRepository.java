package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.SocketChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<SocketChatMessage, Integer> {

    Page<SocketChatMessage> findByReceiverIdAndSenderId(Integer ReceiverId, Integer SenderId, Pageable pageable);

}
