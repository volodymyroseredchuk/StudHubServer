package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.SocketChatMessage;
import com.softserve.academy.studhub.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
public class ChatController {

    private ChatService chatService;

    @GetMapping("chat/{receiverId}&&{senderId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getChatMessages(@PathVariable Integer receiverId, @PathVariable Integer senderId, Pageable pageable) {
         Page<SocketChatMessage> pageableMessages = chatService.getMessagesByReceiverAndSender(receiverId, senderId, pageable);
         List<SocketChatMessage> messages = pageableMessages.getContent();
        return ResponseEntity.ok().body(messages);
    }

}
