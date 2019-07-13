package com.softserve.academy.studhub.service.impl;

import com.google.common.collect.Lists;
import com.softserve.academy.studhub.dto.ChatHeaderDTO;
import com.softserve.academy.studhub.dto.ChatListItem;
import com.softserve.academy.studhub.dto.ChatMessagePostDTO;
import com.softserve.academy.studhub.entity.Chat;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.entity.ChatSubscription;
import com.softserve.academy.studhub.entity.User;
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
        List<ChatSubscription> chatSubscriptions = subscriptionRepository.findChatSubscriptionByChatId(message.getChat().getId());
        List<Integer> receiverIds = new ArrayList<>();
        for (ChatSubscription sub : chatSubscriptions) {
            if (!sub.getUser().getId().equals(message.getSender().getId())) {
                receiverIds.add(sub.getUser().getId());
            }
        }
        socketService.sendChatMessage(receiverIds, message);
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
            List<ChatSubscription> subscribtionsList = subscriptionRepository.findChatSubscriptionByChatId(chatId);
            String chatName;
            if (subscribtionsList.size() == 2) {
                chatName = "Default name";
                for (ChatSubscription subscription : subscribtionsList) {
                    if (!subscription.getUser().getId().equals(userId)) {
                        chatName = subscription.getUser().getUsername();
                        break;
                    }
                }
            } else {
                chatName = sub.getChat().getName();
            }
            if (message.isPresent()) {
                ChatMessage msg = message.get();
                ChatListItem item = new ChatListItem(chatId, msg.getSender().getImageUrl(), chatName, msg.getContent());
                msgList.add(item);
            }
        }
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

    @Override
    public ChatHeaderDTO getChatHeader(Integer chatId, Integer userId) {
        if (chatId == null) {
            throw new IllegalArgumentException("Cannot get header for an empty chat ID");
        }
        Optional<ChatMessage> message = chatMessageRepository.findFirstChatMessageByChatIdOrderByCreationDateTimeDesc(chatId);
        List<ChatSubscription> subscriptionList = subscriptionRepository.findChatSubscriptionByChatId(chatId);
        String chatName;
        String photoUrl = null;
        if (subscriptionList.size() == 2) {
            chatName = "Default name";
            for (ChatSubscription subscription : subscriptionList) {
                if (!subscription.getUser().getId().equals(userId)) {
                    chatName = subscription.getUser().getUsername();
                    photoUrl = subscription.getUser().getImageUrl();
                    break;
                }
            }
        } else {
            Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new IllegalArgumentException("Chat not fofund."));
            chatName = chat.getName();
        }
        ChatMessage msg = message.orElseThrow(() -> new IllegalArgumentException("Message not found."));
        return new ChatHeaderDTO(chatName, photoUrl);
    }

    @Override
    public Integer createChat(Integer creatorUserId, Integer userId) {
        List<ChatSubscription> creatorSubs = subscriptionRepository.findChatSubscriptionByUserId(creatorUserId);
        List<ChatSubscription> receiverSubs = subscriptionRepository.findChatSubscriptionByUserId(userId);

        List<Chat> commonChats = new ArrayList<>();

        for (ChatSubscription cSub : creatorSubs) {
            for (ChatSubscription rSub : receiverSubs) {
                if (cSub.getChat().getId().equals(rSub.getChat().getId())) {
                    commonChats.add(cSub.getChat());
                }
            }
        }

        if (commonChats.size() == 1) {
            return commonChats.get(0).getId();
        } else if (commonChats.size() > 1) {
            Integer chatId = 0;
            for (Chat chat : commonChats) {
                List<ChatSubscription> chatSubs = subscriptionRepository.findChatSubscriptionByChatId(chat.getId());
                if (chatSubs.size() == 2) {
                    chatId = chat.getId();
                    break;
                }
            }
            if (chatId != 0) {
                return chatId;
            }
        }
        Chat chat = chatRepository.saveAndFlush(new Chat());
        ChatSubscription creatorSubscription = new ChatSubscription();
        creatorSubscription.setChat(chat);
        creatorSubscription.setUser(userService.findById(creatorUserId));
        subscriptionRepository.saveAndFlush(creatorSubscription);
        ChatSubscription subscription = new ChatSubscription();
        subscription.setUser(userService.findById(userId));
        subscription.setChat(chat);
        subscriptionRepository.saveAndFlush(subscription);
        return chat.getId();

    }

}
