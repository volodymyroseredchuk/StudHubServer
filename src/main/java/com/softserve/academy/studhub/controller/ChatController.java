package com.softserve.academy.studhub.controller;

import com.google.common.collect.Lists;
import com.softserve.academy.studhub.dto.ChatListItem;
import com.softserve.academy.studhub.dto.ChatMessagePostDTO;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.service.ChatService;
import com.softserve.academy.studhub.service.impl.OffsetBasedPageable;
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
    public ResponseEntity<?> getChatMessages(@PathVariable Integer chatId, @RequestParam Integer offset, @RequestParam Integer size) {
        List<ChatMessage> messages = chatService.getMessagesByChatId(chatId, new OffsetBasedPageable(offset, size));
        return ResponseEntity.ok().body(messages);
    }

    @GetMapping("/chat/list/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getChatMessages(@PathVariable Integer userId) {
        List<ChatListItem> itemList = chatService.getChatList(userId);
        return ResponseEntity.ok().body(itemList);
    }

    @GetMapping("/chat/header/{chatId}/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getChatHeader(@PathVariable Integer chatId, @PathVariable Integer userId) {
        return ResponseEntity.ok().body(chatService.getChatHeader(chatId, userId));
    }

    @PostMapping("/chat")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> postChatMessage(@RequestBody ChatMessagePostDTO message) {
        ChatMessage savedMessage = chatService.save(message);
        chatService.handleChatMessage(savedMessage);
        return ResponseEntity.ok().body(savedMessage);
    }

    @GetMapping("/chat/new/{creatorUserId}/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> postChat(@PathVariable Integer creatorUserId, @PathVariable Integer userId) {
        return ResponseEntity.ok().body(chatService.createChat(creatorUserId, userId));
    }

}
