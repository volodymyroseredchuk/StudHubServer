package com.softserve.academy.studhub.service.impl;

import com.google.common.collect.Lists;
import com.softserve.academy.studhub.dto.ChatListItem;
import com.softserve.academy.studhub.dto.ChatMessagePostDTO;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.entity.ChatSubscription;
import com.softserve.academy.studhub.repository.ChatMessageRepository;
import com.softserve.academy.studhub.repository.ChatRepository;
import com.softserve.academy.studhub.repository.ChatSubscriptionRepository;
import com.softserve.academy.studhub.service.ChatService;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private ChatMessageRepository chatMessageRepository;
    private ChatRepository chatRepository;
    private ChatSubscriptionRepository subscriptionRepository;
    private SocketService socketService;
    private UserService userService;

    @Override
    public void handleChatMessage(ChatMessage message) {

        if (message == null) {
            throw new IllegalArgumentException("Cannot send an empty chat message.");
        }

        chatMessageRepository.saveAndFlush(message);

        socketService.sendChatMessage(message);
    }

    @Override
    public List<ChatMessage> getMessagesByChatId(Integer chatId, Pageable pageable) {

        if (chatId == null) {
            throw new IllegalArgumentException("Cannot get messages an empty chat.");
        }
        Page<ChatMessage> page = chatMessageRepository.findByChatIdOrderByCreationDateTimeDesc(chatId, pageable);
        List<ChatMessage> list = page.getContent();
        if (pageable.getPageNumber() == 0) {
            list = Lists.reverse(list);
        }

        return list;
    }

    @Override
    public List<ChatListItem> getChatList(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Cannot get chat list by empty ID.");
        }
        List<ChatSubscription> subscriptions =  subscriptionRepository.findDistinctChatSubscriptionByUserId(userId);
        List<ChatListItem> msgList = new ArrayList<>();
        for(ChatSubscription sub : subscriptions) {
            Integer chatId = sub.getChat().getId();
            Optional<ChatMessage> message = chatMessageRepository.findFirstChatMessageByChatIdOrderByCreationDateTimeDesc(chatId);
            if (message.isPresent()) {
                ChatMessage msg = message.get();
                ChatListItem item = new ChatListItem(chatId, msg.getSender().getImageUrl(), msg.getSender().getUsername(), msg.getContent());
                msgList.add(item);
            }
        }
        System.out.println(msgList);
        return msgList;
    }

    @Override
    public ChatMessage save(ChatMessagePostDTO messagePostDTO) {
        if (messagePostDTO == null) {
            throw new IllegalArgumentException("Cannot save an empty message.");
        }
        ChatMessage message = new ChatMessage();
        message.setContent(messagePostDTO.getContent());
        message.setSender(userService.findById(messagePostDTO.getSender()));
        message.setCreationDateTime(messagePostDTO.getCreationDateTime());
        message.setChat(chatRepository.findById(messagePostDTO.getChat())
                .orElseThrow(() -> new IllegalArgumentException("Cannot save message for not existing chat.")));
        return chatMessageRepository.saveAndFlush(message);
    }

}
