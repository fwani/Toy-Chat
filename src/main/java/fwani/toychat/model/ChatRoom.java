package fwani.toychat.model;

import fwani.grpc.ChatMessage;
import fwani.grpc.ChatType;
import fwani.grpc.ServerMessage;
import io.grpc.stub.StreamObserver;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class ChatRoom {
    private final String id = UUID.randomUUID().toString();
    private String name;
    private final Set<StreamObserver<ServerMessage>> observers = ConcurrentHashMap.newKeySet();

    public ChatRoom(String name) {
        if (name == null) {
            name = id;
        }
        this.name = name;
    }

    public void broadcastMessage(ChatMessage chatMessage, StreamObserver<ServerMessage> senderObserver) {
        if (chatMessage.getType().equals(ChatType.JOIN)) {
            var responseMessage = ServerMessage.newBuilder()
                    .setMessage(chatMessage.getUser() + " 님이 접속 하였습니다.")
                    .setType(chatMessage.getType())
                    .build();
            for (var observer : observers) {
                observer.onNext(responseMessage);
            }
        } else if (chatMessage.getType().equals(ChatType.LEAVE)) {
            var responseMessage = ServerMessage.newBuilder()
                    .setMessage(chatMessage.getUser() + " 님이 퇴장 하였습니다.")
                    .setType(chatMessage.getType())
                    .build();
            for (var observer : observers) {
                observer.onNext(responseMessage);
            }
        } else if (chatMessage.getType().equals(ChatType.MESSAGE)) {
            var responseMessage = ServerMessage.newBuilder()
                    .setMessage(chatMessage.getUser() + " 님이 퇴장 하였습니다.")
                    .setType(chatMessage.getType())
                    .build();
            for (var observer : observers) {
                if (observer.equals(senderObserver)) continue;
                observer.onNext(responseMessage);
            }
        } else {
            var responseMessage = ServerMessage.newBuilder()
                    .setMessage(chatMessage.getMessage())
                    .setType(chatMessage.getType())
                    .build();
            for (var observer : observers) {
                observer.onNext(responseMessage);
            }
        }
    }

    public void addObserver(StreamObserver<ServerMessage> observer) {
        observers.add(observer);
    }

    public void removeObserver(StreamObserver<ServerMessage> observer) {
        observers.remove(observer);
    }

    public void closeRoom() {

    }
}
