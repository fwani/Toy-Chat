package fwani.toychat.business;

import fwani.toychat.model.ChatRoom;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class ChatRoomManager {
    private final Map<String, ChatRoom> chatRooms = new HashMap<>();

    public ChatRoom openChatRoom(String name) {
        var newChatRoom = new ChatRoom(name);
        chatRooms.put(newChatRoom.getId(), newChatRoom);
        return newChatRoom;
    }

    public ChatRoom getChatRoom(String id) {
        return chatRooms.get(id);
    }

    public void closeChatRoom(String name) {
        ChatRoom chatRoom;
        synchronized (chatRooms) {
            chatRoom = chatRooms.remove(name);
        }
        chatRoom.closeRoom();
    }
}
