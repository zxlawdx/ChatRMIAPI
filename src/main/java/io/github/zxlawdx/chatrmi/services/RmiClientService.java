package io.github.zxlawdx.chatrmi.services;

import java.rmi.Naming;

import org.springframework.stereotype.Service;
import  chatrmi.remote.LoginService;
import chatrmi.remote.LogonService;
import chatrmi.remote.SendMessageService;

@Service
public class RmiClientService {

    private LoginService getLoginService() {
        try {
            return (LoginService) Naming.lookup("rmi://localhost:1099/Login");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no serviço de login", e);
        }
    }

    private LogonService getLogonService() {
        try {
            return (LogonService) Naming.lookup("rmi://localhost:1099/Logon");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no serviço de cadastro", e);
        }
    }

    private SendMessageService getSendMessageService() {
        try {
            return (SendMessageService) Naming.lookup("rmi://localhost:1099/SendMessage");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no serviço de mensagens", e);
        }
    }

    public boolean login(String email, String password) {
        try {
            return getLoginService().login(email, password);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer login via RMI", e);
        }
    }

    public void register(String username, String email, String password) {
        try {
            getLogonService().createUser(username, email, password);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar usuário via RMI", e);
        }
    }

    public String sendMessage(String senderEmail, String receiverEmail, String message) {
        try {
            return getSendMessageService().sendMessage(senderEmail, receiverEmail, message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem via RMI", e);
        }
    }
}