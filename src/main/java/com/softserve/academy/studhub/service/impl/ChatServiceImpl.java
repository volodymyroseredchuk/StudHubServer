package com.softserve.academy.studhub.service.impl;

import com.google.common.collect.Lists;
import com.softserve.academy.studhub.dto.ChatHeaderDTO;
import com.softserve.academy.studhub.dto.ChatListItem;
import com.softserve.academy.studhub.dto.ChatMessagePostDTO;
import com.softserve.academy.studhub.entity.Chat;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.entity.ChatSubscription;
import com.softserve.academy.studhub.repository.ChatMessageRepository;
import com.softserve.academy.studhub.repository.ChatRepository;
import com.softserve.academy.studhub.repository.ChatSubscriptionRepository;
import com.softserve.academy.studhub.service.ChatService;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private ChatMessageRepository chatMessageRepository;
    private ChatRepository chatRepository;
    private ChatSubscriptionRepository subscriptionRepository;
    private SocketService socketService;
    private UserService userService;

    public static final LocalDateTime DEFAULT_CREATION_TIME = LocalDateTime.parse("1999-12-11T10:15:30");

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
        List<ChatSubscription> subscriptions =  subscriptionRepository.findChatSubscriptionByUserId(userId);
        Map<ChatMessage, ChatListItem> listItemsMap = new HashMap<>();

        for(ChatSubscription sub : subscriptions) {
            Map.Entry<ChatMessage, ChatListItem> entry = getListItemEntry(sub, userId);
            listItemsMap.put(entry.getKey(), entry.getValue());
        }

        return sortByCreationDateTime(listItemsMap);
    }

    private Map.Entry<ChatMessage, ChatListItem> getListItemEntry(ChatSubscription sub, Integer userId) {
        Integer chatId = sub.getChat().getId();
        List<ChatSubscription> subscriptionList = subscriptionRepository.findChatSubscriptionByChatId(chatId);

        if (subscriptionList.size() <= 2) {
            return generateListItemPrivate(subscriptionList, userId, sub);
        } else {
            return generateListItemGroup(subscriptionList, userId, sub);
        }

    }

    private Map.Entry<ChatMessage, ChatListItem> generateListItemPrivate(List<ChatSubscription> subscriptionList,
                                                                         Integer userId, ChatSubscription sub) {
        Integer chatId = sub.getChat().getId();
        Optional<ChatMessage> message = chatMessageRepository.findFirstChatMessageByChatIdOrderByCreationDateTimeDesc(chatId);

        ChatMessage msg =  message.orElseThrow(() -> new IllegalArgumentException("Could not get last chat message."));


        String chatName = null;
        String photoUrl = null;
        for (ChatSubscription subscription : subscriptionList) {
            if (!subscription.getUser().getId().equals(userId)) {
                chatName = subscription.getUser().getUsername();
                photoUrl = subscription.getUser().getImageUrl();
                break;
            }
        }
        ChatListItem item = new ChatListItem(chatId, photoUrl, chatName, msg.getContent());

        return new AbstractMap.SimpleEntry<>(msg, item);
    }

    private Map.Entry<ChatMessage, ChatListItem> generateListItemGroup(List<ChatSubscription> subscriptionList,
                                                                       Integer userId, ChatSubscription sub) {
        Integer chatId = sub.getChat().getId();
        Optional<ChatMessage> message = chatMessageRepository.findFirstChatMessageByChatIdOrderByCreationDateTimeDesc(chatId);

        ChatMessage msg =  message.orElseThrow(() -> new IllegalArgumentException("Could not get last chat message."));

        String chatName;
        String photoUrl = null;
        chatName = sub.getChat().getName();
        for (ChatSubscription subscription : subscriptionList) {
            if (!subscription.getUser().getId().equals(userId)) {
                photoUrl = subscription.getUser().getImageUrl();
                break;
            }
        }
        ChatListItem item = new ChatListItem(sub.getChat().getId(), photoUrl, chatName, msg.getContent());

        return new AbstractMap.SimpleEntry<>(msg, item);
    }

    private List<ChatListItem> sortByCreationDateTime(Map<ChatMessage, ChatListItem> listItemMap) {

        List<ChatListItem> itemList = new ArrayList<>();
        List<ChatMessage> messagesForSorting = new ArrayList<>(listItemMap.keySet());
        messagesForSorting.sort(Comparator.comparing(ChatMessage::getCreationDateTime).reversed());
        for (ChatMessage message : messagesForSorting) {
            itemList.add(listItemMap.get(message));
        }

        return itemList;
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
            Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new IllegalArgumentException("Chat not found."));
            chatName = chat.getName();
        }
        return new ChatHeaderDTO(chatName, photoUrl);
    }

    @Override
    public Integer getChatId(Integer creatorUserId, Integer userId) {
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

        return createNewChat(creatorUserId, userId).getId();

    }

    private Chat createNewChat(Integer creatorUserId, Integer userId) {

        Chat chat = chatRepository.saveAndFlush(new Chat());

        ChatSubscription creatorSubscription = new ChatSubscription();
        creatorSubscription.setChat(chat);
        creatorSubscription.setUser(userService.findById(creatorUserId));
        subscriptionRepository.saveAndFlush(creatorSubscription);

        ChatSubscription subscription = new ChatSubscription();
        subscription.setChat(chat);
        subscription.setUser(userService.findById(userId));
        subscriptionRepository.saveAndFlush(subscription);

        ChatMessage defaultMessage = new ChatMessage();
        defaultMessage.setCreationDateTime(DEFAULT_CREATION_TIME);
        defaultMessage.setChat(chat);
        defaultMessage.setContent("Chat successfully created.");
        defaultMessage.setSender(null);
        chatMessageRepository.saveAndFlush(defaultMessage);

        return chat;
    }

    public List<String> getUsernameParticipantsByChat(Integer chatId) {
        List<ChatSubscription> subscriptions = subscriptionRepository.findChatSubscriptionByChatId(chatId);
        List<String> usernames = new ArrayList<>();
        for (ChatSubscription sub : subscriptions) {
            usernames.add(sub.getUser().getUsername());
        }
        return usernames;
    }

}
