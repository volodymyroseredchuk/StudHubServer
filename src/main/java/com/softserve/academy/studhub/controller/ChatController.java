package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.ChatListItemDTO;
import com.softserve.academy.studhub.dto.ChatMessagePostDTO;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.service.ChatService;
import com.softserve.academy.studhub.service.UserService;
import com.softserve.academy.studhub.service.impl.OffsetBasedPageable;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private ChatService chatService;

    @GetMapping("/{chatId}")
    @PreAuthorize("isAuthenticated() "
            + "and @chatServiceImpl.getUsernameParticipantsByChat(#chatId).contains(principal.username)")
    public ResponseEntity<?> getChatMessages(@PathVariable Integer chatId, @RequestParam Integer offset, @RequestParam Integer size) {
        List<ChatMessage> messages = chatService.getMessagesByChatId(chatId, new OffsetBasedPageable(offset, size));
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/list/{userId}")
    @PreAuthorize("isAuthenticated() and @userServiceImpl.findById(#userId).username == principal.username")
    public ResponseEntity<?> getChatMessages(@PathVariable Integer userId) {
        List<ChatListItemDTO> itemList = chatService.getChatList(userId);
        return ResponseEntity.ok(itemList);
    }

    @GetMapping("/header/{chatId}/{userId}")
    @PreAuthorize("isAuthenticated()"
            + "and @chatServiceImpl.getUsernameParticipantsByChat(#chatId).contains(principal.username)"
            + "and @userServiceImpl.findById(#userId).username == principal.username")
    public ResponseEntity<?> getChatHeader(@PathVariable Integer chatId, @PathVariable Integer userId) {
        return ResponseEntity.ok(chatService.getChatHeader(chatId, userId));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()"
            + "and @chatServiceImpl.getUsernameParticipantsByChat(#message.chat).contains(principal.username)"
            + "and @userServiceImpl.findById(#message.sender).username == principal.username")
    public ResponseEntity<?> postChatMessage(@RequestBody ChatMessagePostDTO message) {
        ChatMessage savedMessage = chatService.save(message);
        chatService.handleChatMessage(savedMessage);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/new/{creatorUserId}/{userId}/{secret}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getChat(@PathVariable Integer creatorUserId, @PathVariable Integer userId, @PathVariable Boolean secret) {
        return ResponseEntity.ok(chatService.getChatId(creatorUserId, userId, secret));
    }

}
