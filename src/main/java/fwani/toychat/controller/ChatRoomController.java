package fwani.toychat.controller;


import fwani.toychat.model.ChatRoom;
import fwani.toychat.business.ChatRoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/chat/room")
@RestController
public class ChatRoomController {
    @Autowired
    private ChatRoomManager chatRoomManager;

    @GetMapping("/open")
    public String openChatRoom(@RequestParam(required = false) String name) {
        var chatRoom = chatRoomManager.openChatRoom(name);
        return chatRoom.getId();
    }

    @GetMapping("")
    public List<String> getChatRoomList() {
        var chatRooms = chatRoomManager.getChatRooms();
        return chatRooms.values().stream().map(ChatRoom::getId).collect(Collectors.toList());
    }
}
