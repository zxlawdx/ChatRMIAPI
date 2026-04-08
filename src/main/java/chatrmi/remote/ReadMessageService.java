package chatrmi.remote;

import chatrmi.dto.ChatMessageDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ReadMessageService extends Remote {

    List<ChatMessageDto> getConversation(
            String sessionUserEmail,
            String otherEmail
    ) throws RemoteException;

}