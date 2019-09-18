package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.dto.ChatListItemDTO;
import com.softserve.academy.studhub.dto.ChatMessagePostDTO;
import com.softserve.academy.studhub.entity.Chat;
import com.softserve.academy.studhub.entity.ChatMessage;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.repository.ChatMessageRepository;
import com.softserve.academy.studhub.repository.ChatRepository;
import com.softserve.academy.studhub.repository.ChatSubscriptionRepository;
import com.softserve.academy.studhub.service.ChatService;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatServiceImplTest {

    private ChatService chatService;

    @Mock
    private ChatMessageRepository chatMessageRepository;
    @Mock
    private ChatRepository chatRepository;
    @Mock
    private ChatSubscriptionRepository subscriptionRepository;
    @Mock
    private SocketService socketService;
    @Mock
    private UserService userService;

    @Before
    public void initialize() {
        chatService = new ChatServiceImpl(chatMessageRepository, chatRepository, subscriptionRepository, socketService, userService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMessagesByChatIdNull() {
        chatService.getMessagesByChatId(null, null);
        chatService.getMessagesByChatId(1, null);
    }

    @Test
    public void getChatList() {
        List<ChatListItemDTO> chatListItemDTOList = new ArrayList<>();
        when(chatRepository.findChatListByUserId(1)).thenReturn(chatListItemDTOList);
        Assert.assertEquals(chatService.getChatList(1), chatListItemDTOList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getChatListNull() {
        chatService.getChatList(null);
    }

    /*@Test
    public void save() {
        LocalDateTime time = LocalDateTime.now();
        ChatMessagePostDTO messagePostDTO = new ChatMessagePostDTO();
        messagePostDTO.setChat(1);
        messagePostDTO.setContent("content");
        messagePostDTO.setCreationDateTime(time);
        messagePostDTO.setId(1);
        messagePostDTO.setSender(1);

        ChatMessage message = new ChatMessage();
        Chat chat = new Chat();
        chat.setId(1);
        message.setChat(chat);
        message.setContent("content");
        message.setCreationDateTime(time);
        User user = new User();
        user.setId(1);
        message.setSender(user);
        message.setId(1);

        when(userService.findById(1)).thenReturn(user);
        when(chatRepository.findById(1)).thenReturn(Optional.of(chat));
        when(chatMessageRepository.saveAndFlush(message)).thenReturn(message);

        Assert.assertEquals(chatService.save(messagePostDTO), message);

    }*/

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() {
        chatService.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getChatHeaderNull() {
        chatService.getChatHeader(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getChatIdNull() {
        chatService.getChatId(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUsernameParticipantsByChatNull() {
        chatService.getUsernameParticipantsByChat(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findUserIdByUserIdNotAndChatIdNull() {
        chatService.findUserIdByUserIdNotAndChatId(null, null);
    }
}