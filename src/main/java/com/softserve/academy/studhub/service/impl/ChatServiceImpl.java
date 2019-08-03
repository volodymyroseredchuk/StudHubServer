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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        if (chatId == null || pageable == null) {
            throw new IllegalArgumentException("Cannot get messages an empty chat or empty pagination settings.");
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
        Boolean secret = sub.getChat().getSecret();
        List<ChatSubscription> chatSubscriptions = subscriptionRepository.findChatSubscriptionByChatId(chatId);
        Optional<ChatMessage> message = chatMessageRepository.findFirstChatMessageByChatIdOrderByCreationDateTimeDesc(chatId);

        ChatMessage lastMessage =  message.orElseThrow(() -> new IllegalArgumentException("Could not get last chat message."));

        String chatName = null;
        String photoUrl = null;
        for (ChatSubscription subscription : chatSubscriptions) {
            if (!subscription.getUser().getId().equals(userId)) {
                chatName = subscription.getUser().getUsername();
                photoUrl = subscription.getUser().getImageUrl();
                break;
            }
        }

        boolean personal = (chatSubscriptions.size() == 2);
        ChatListItem item = new ChatListItem(chatId, personal ? photoUrl : null,
                personal ? chatName : sub.getChat().getName(), lastMessage.getContent(), secret);

        return new AbstractMap.SimpleEntry<>(lastMessage, item);

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
        if (chatId == null || userId == null) {
            throw new IllegalArgumentException("Cannot get header for an empty chat ID or empty user ID.");
        }

        List<ChatSubscription> subscriptionList = subscriptionRepository.findChatSubscriptionByChatId(chatId);
        String chatName = null;
        String photoUrl = null;
        if (subscriptionList.size() == 2) {
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
    public Integer getChatId(Integer creatorUserId, Integer userId, Boolean secret) {
        if (creatorUserId == null || userId == null) {
            throw new IllegalArgumentException("Cannot get chat ID for an empty user.");
        }

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

        if (commonChats.size() == 1 && commonChats.get(0).getSecret().equals(secret)) {
            return commonChats.get(0).getId();
        } else if (commonChats.size() > 1) {
            for (Chat chat : commonChats) {
                List<ChatSubscription> chatSubs = subscriptionRepository.findChatSubscriptionByChatId(chat.getId());
                if (chatSubs.size() == 2 && chat.getSecret().equals(secret)) {
                    return chat.getId();
                }
            }
        }

        return createNewChat(creatorUserId, userId, secret).getId();

    }

    private Chat createNewChat(Integer creatorUserId, Integer userId, Boolean secret) {
        Chat chatToSave = new Chat();
        chatToSave.setSecret(secret);
        Chat chat = chatRepository.saveAndFlush(chatToSave);

        ChatSubscription creatorSubscription = new ChatSubscription();
        creatorSubscription.setChat(chat);
        creatorSubscription.setUser(userService.findById(creatorUserId));
        subscriptionRepository.saveAndFlush(creatorSubscription);

        ChatSubscription subscription = new ChatSubscription();
        subscription.setChat(chat);
        subscription.setUser(userService.findById(userId));
        subscriptionRepository.saveAndFlush(subscription);

        ChatMessage defaultMessage = new ChatMessage();
        defaultMessage.setCreationDateTime(LocalDateTime.now());
        defaultMessage.setChat(chat);
        defaultMessage.setContent("Chat successfully created.");
        defaultMessage.setSender(null);
        chatMessageRepository.saveAndFlush(defaultMessage);

        return chat;
    }

    public List<String> getUsernameParticipantsByChat(Integer chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("Cannot get participants for an empty chat ID.");
        }

        List<ChatSubscription> subscriptions = subscriptionRepository.findChatSubscriptionByChatId(chatId);
        List<String> usernames = new ArrayList<>();
        for (ChatSubscription sub : subscriptions) {
            usernames.add(sub.getUser().getUsername());
        }
        return usernames;
    }

    @Override
    public void testPerformance() {
        System.out.println(LocalDateTime.now());
        for(int i = 0; i < 1000; i++) {
            User user = new User();
            user.setCreationDate(LocalDate.now());
            user.setEmail("test" + i + "@mail.com");
            user.setFirstName("TestF" + i);
            user.setImageUrl(null);
            user.setLastName("TestL" + i);
            user.setPassword(BCrypt.hashpw(Integer.toString(i) + Integer.toString(i) + Integer.toString(i) + Integer.toString(i) + Integer.toString(i) + Integer.toString(i), BCrypt.gensalt()));
            user.setUsername("TestU" + i);
            user.setCookiesCount(1000);
            user.setUniversity(null);
            user.setEmailSubscription(false);
            user.setGooglePassword(null);
            user.setIsActivated(true);
            user = userService.add(user);

            User user1 = new User();
            user1.setCreationDate(LocalDate.now());
            user1.setEmail("testSecond" + i + "@mail.com");
            user1.setFirstName("TestFN" + i);
            user1.setImageUrl(null);
            user1.setLastName("TestLN" + i);
            user1.setPassword(BCrypt.hashpw(Integer.toString(i) + Integer.toString(i) + Integer.toString(i) + Integer.toString(i) + Integer.toString(i) + Integer.toString(i), BCrypt.gensalt()));
            user1.setUsername("TestUN" + i);
            user1.setCookiesCount(1000);
            user1.setUniversity(null);
            user1.setEmailSubscription(false);
            user1.setGooglePassword(null);
            user1.setIsActivated(true);
            user1 = userService.add(user1);

            Chat chat = new Chat();
            chat.setName("test" + i);
            chat = chatRepository.saveAndFlush(chat);

            ChatSubscription chatSubscription = new ChatSubscription();
            chatSubscription.setChat(chat);
            chatSubscription.setUser(user);
            chatSubscription = subscriptionRepository.saveAndFlush(chatSubscription);

            ChatSubscription chatSubscription1 = new ChatSubscription();
            chatSubscription1.setUser(user1);
            chatSubscription1.setChat(chat);
            chatSubscription1 = subscriptionRepository.saveAndFlush(chatSubscription1);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setCreationDateTime(LocalDateTime.now());
            chatMessage.setSender(user);
            chatMessage.setContent("Test" + i);
            chatMessage.setChat(chat);
            chatMessage = chatMessageRepository.saveAndFlush(chatMessage);

            ChatMessage chatMessage1 = new ChatMessage();
            chatMessage1.setContent("Test2" + i);
            chatMessage1.setSender(user1);
            chatMessage1.setCreationDateTime(LocalDateTime.now());
            chatMessage1.setChat(chat);
            chatMessage1 = chatMessageRepository.saveAndFlush(chatMessage1);
        }
        System.out.println(LocalDateTime.now());
    }

    public void testPerformance2() {
        System.out.println(LocalDateTime.now());
        User user = userService.findByUsername("avash");
        for (int i = 0; i < 1000; i++) {
            Chat chat = chatRepository.findByName("Test" + i);
            ChatSubscription chatSubscription = new ChatSubscription();
            chatSubscription.setChat(chat);
            chatSubscription.setUser(user);
            subscriptionRepository.saveAndFlush(chatSubscription);
        }
        System.out.println(LocalDateTime.now());
    }

    public List<Integer> findUserIdByUserIdNotAndChatId(Integer userId, Integer chatId) {
        List<ChatSubscription> subs = subscriptionRepository.findChatSubscriptionByChatId(chatId);
        List<Integer> subIds = new ArrayList<>();
        for (ChatSubscription sub : subs) {
            if (!sub.getUser().getId().equals(userId)) {
                subIds.add(sub.getUser().getId());
            }
        }
        return subIds;
    }

    @Override
    @Transactional
    public void deleteChat(Integer chatId) {
        chatMessageRepository.deleteAllByChatId(chatId);
        subscriptionRepository.deleteAllByChatId(chatId);
        chatRepository.deleteById(chatId);
    }


}
