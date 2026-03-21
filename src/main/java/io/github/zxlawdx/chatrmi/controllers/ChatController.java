package io.github.zxlawdx.chatrmi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.zxlawdx.chatrmi.dto.LoginRequest;
import io.github.zxlawdx.chatrmi.dto.MessageRequest;
import io.github.zxlawdx.chatrmi.dto.RegisterRequest;
import io.github.zxlawdx.chatrmi.services.RmiClientService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private final RmiClientService rmiClientService;

    public ChatController(RmiClientService rmiClientService) {
        this.rmiClientService = rmiClientService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean success = rmiClientService.login(request.getEmail(), request.getPassword());

        if (success) {

            return ResponseEntity.ok("Login realizado com sucesso");
        }

        return ResponseEntity.status(401).body("Email ou senha inválidos");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        rmiClientService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest request) {
        String response = rmiClientService.sendMessage(
                request.getSenderEmail(),
                request.getReceiverEmail(),
                request.getMessage()
        );

        return ResponseEntity.ok(response);
    }
}