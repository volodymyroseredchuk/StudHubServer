package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.ChatHeaderDTO;
import com.softserve.academy.studhub.dto.ChatListItemDTO;
import com.softserve.academy.studhub.dto.ChatMessagePostDTO;
import com.softserve.academy.studhub.entity.ChatMessage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {

    void handleChatMessage(ChatMessage message);
    List<ChatMessage> getMessagesByChatId(Integer chatId, Pageable pageable);
    List<ChatListItemDTO> getChatList(Integer userId);
    ChatMessage save(ChatMessagePostDTO messagePostDTO);
    ChatHeaderDTO getChatHeader(Integer chatId, Integer userId);
    Integer getChatId(Integer creatorUserId, Integer userId, Boolean secret);
    List<String> getUsernameParticipantsByChat(Integer chatId);
    List<Integer> findUserIdByUserIdNotAndChatId(Integer userId, Integer chatId);
    void deleteChat(Integer chatId);

}
