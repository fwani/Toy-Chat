package fwani.toychat.business;

import fwani.grpc.ChatMessage;
import fwani.grpc.ChatServiceGrpc;
import fwani.grpc.ServerMessage;
import fwani.toychat.model.ChatRoom;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GrpcService
public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    @Autowired
    private ChatRoomManager chatRoomManager;

    @Override
    public StreamObserver<ChatMessage> sendMessage(StreamObserver<ServerMessage> responseObserver) {
        return new StreamObserver<>() {
            ChatRoom chatRoom;

            @Override
            public void onNext(ChatMessage chatMessage) {
                System.out.println(chatMessage);
                if (chatRoom == null) {
                    chatRoom = chatRoomManager.getChatRoom(chatMessage.getChatRoomId());
                }
                chatRoom.addObserver(responseObserver);
                chatRoom.broadcastMessage(chatMessage, responseObserver);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                chatRoom.removeObserver(responseObserver);
            }
        };
    }
}
