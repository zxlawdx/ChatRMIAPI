package chatrmi.services;

import chatrmi.dto.ChatMessageDto;
import chatrmi.dto.UserSummaryDto;
import chatrmi.remote.LoginService;
import chatrmi.remote.LogonService;
import chatrmi.remote.ReadMessageService;
import chatrmi.remote.SendMessageService;
import chatrmi.remote.UserQueryService;
import chatrmi.dto.ChatMessageResponse;
import chatrmi.dto.UserSummaryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.util.List;

@Service
public class RmiClientService {

    @Value("${app.rmi.host}")
    private String host;

    @Value("${app.rmi.port}")
    private int port;

    private Registry getRegistry() {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            System.out.println("Conectando no registry RMI em " + host + ":" + port);
            System.out.println("Serviços visíveis pela API:");
            for (String name : registry.list()) {
                System.out.println("- " + name);
            }
            return registry;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no registry RMI" + e.getMessage(), e);
        }
    }

    private LoginService getLoginService() {
        try {
            return (LoginService) getRegistry().lookup("Login");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no serviço de login" + e.getMessage(), e);
        }
    }

    private LogonService getLogonService() {
        try {
            return (LogonService) getRegistry().lookup("Logon");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no serviço de cadastro" + e.getMessage(), e);
        }
    }

    private SendMessageService getSendMessageService() {
        try {
            return (SendMessageService) getRegistry().lookup("SendMessage");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no serviço de envio de mensagem" + e.getMessage(), e);
        }
    }

    private ReadMessageService getReadMessageService() {
        try {
            return (ReadMessageService) getRegistry().lookup("ReadMessage");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no serviço de leitura de mensagens" + e.getMessage(), e);
        }
    }

    private UserQueryService getUserQueryService() {
        try {
            return (UserQueryService) getRegistry().lookup("UserQuery");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no serviço de usuários" + e.getMessage(), e);
        }
    }

    public boolean login(String email, String password) {
        try {
            return getLoginService().login(email, password);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer login via RMI" + e.getMessage(), e);
        }
    }

    public void register(String username, String email, String password) {
        try {
            getLogonService().createUser(username, email, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar usuário via RMI: " + e.getMessage(), e);
        }
    }

    public String sendMessage(String senderEmail, String receiverEmail, String message) {
        try {
            return getSendMessageService().sendMessage(senderEmail, receiverEmail, message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem via RMI: " + e.getMessage(), e);
        }
    }

    public List<ChatMessageResponse> getConversation(String myEmail, String otherEmail) {
        try {
            List<ChatMessageDto> messages =
                    getReadMessageService().getConversation(myEmail, otherEmail);

            return messages.stream()
            .map(m -> new ChatMessageResponse(
                    m.getId(),
                    m.getSenderEmail(),
                    m.getReceiverEmail(),
                    m.getMessage(),
                    m.getSentAt()
            ))
            .toList();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar conversa via RMI: " + e.getMessage(), e);
        }
    }
    

    public List<UserSummaryResponse> listUsers(String myEmail) {
        try {
            List<UserSummaryDto> users = getUserQueryService().listUsers(myEmail);

            return users.stream()
                    .map(u -> new UserSummaryResponse(
                            u.getUsername(),
                            u.getEmail()
                    ))
                    .toList();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar usuários via RMI: " + e.getMessage(), e);
        }
    }
}