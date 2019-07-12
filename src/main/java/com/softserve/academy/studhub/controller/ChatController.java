package com.softserve.academy.studhub.controller;

import com.google.common.collect.Lists;
import com.softserve.academy.studhub.dto.ChatListItem;
import com.softserve.academy.studhub.dto.ChatMessagePostDTO;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
public class ChatController {

    private ChatService chatService;

    @GetMapping("/chat/{chatId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getChatMessages(@PathVariable Integer chatId, Pageable pageable) {
        List<ChatMessage> messages = chatService.getMessagesByChatId(chatId, pageable);
        return ResponseEntity.ok().body(messages);
    }

    @GetMapping("/chat/list/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getChatMessages(@PathVariable Integer userId) {
        List<ChatListItem> itemList = chatService.getChatList(userId);
        return ResponseEntity.ok().body(itemList);
    }

    @PostMapping("/chat")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> postChatMessage(@RequestBody ChatMessagePostDTO message) {
        ChatMessage savedMessage = chatService.save(message);
        chatService.handleChatMessage(savedMessage);
        return ResponseEntity.ok().body(savedMessage);
    }

}
