package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.dto.ChatListItemDTO;
import com.softserve.academy.studhub.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    Chat findByName(String name);
    void deleteById(Integer chatId);

    // For the performance only...
    @Query(value = "select mainQuery.chatId, mainQuery.secret, mainQuery.content as lastMessageText,"
            + " userQuery.user_name as username, userQuery.image_url as photoUrl from (\n"
            + "  select chats.id as chatId, chats.secret, contentQuery.content, contentQuery.lastMessageTime from chats\n"
            + "  inner join chat_subscriptions\n"
            + "  on chat_subscriptions.chat_id = chats.id\n"
            + "  and chat_subscriptions.user_id = :userId\n" +
            "  left join (\n"
            + "    select M1.content, M1.chat_id, M2.lastMessageTime from chat_messages as M1\n"
            + "    inner join (\n"
            + "      select max(chat_messages.creation_datetime) as lastMessageTime, chat_messages.chat_id from chat_messages\n"
            + "      group by chat_messages.chat_id\n"
            + "    ) as M2\n"
            + "    on M2.chat_id = M1.chat_id\n"
            + "    and M2.lastMessageTime = M1.creation_datetime\n"
            + "    \n"
            + "  ) as contentQuery\n"
            + "  on contentQuery.chat_id = chats.id\n"
            + ") as mainQuery\n"
            + "inner join (\n"
            + "  select users.user_name, users.image_url, chat_subscriptions.chat_id from chat_subscriptions\n"
            + "    inner join users\n"
            + "    on users.id = chat_subscriptions.user_id\n"
            + "  where chat_subscriptions.user_id != :userId\n"
            + "    group by chat_subscriptions.chat_id\n"
            + ") as userQuery\n"
            + "on userQuery.chat_id = mainQuery.chatId\n"
            + "order by mainQuery.lastMessageTime desc;",
            nativeQuery = true)
    List<ChatListItemDTO> findChatListByUserId(@Param("userId") Integer userId);
}
