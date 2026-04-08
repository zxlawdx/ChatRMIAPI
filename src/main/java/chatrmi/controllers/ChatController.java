package chatrmi.controllers;

import chatrmi.dto.*;
import chatrmi.security.JwtService;
import chatrmi.services.RmiClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final RmiClientService rmiClientService;
    private final JwtService jwtService;

    public ChatController(RmiClientService rmiClientService, JwtService jwtService) {
        this.rmiClientService = rmiClientService;
        this.jwtService = jwtService;
    }

    

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        boolean success = rmiClientService.login(request.getEmail(), request.getPassword());

        if (!success) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiMessageResponse("Email ou senha inválidos"));
        }

        String token = jwtService.generateToken(request.getEmail());

        return ResponseEntity.ok(new LoginResponse(
                "Login realizado com sucesso",
                token,
                request.getEmail()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        rmiClientService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiMessageResponse("Usuário cadastrado com sucesso"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        return ResponseEntity.ok(new SessionUserResponse(authentication.getName()));
    }

    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(
            @Valid @RequestBody MessageRequest request,
            Authentication authentication
    ) {
        String senderEmail = authentication.getName();

        String response = rmiClientService.sendMessage(
                senderEmail,
                request.getReceiverEmail(),
                request.getMessage()
        );

        return ResponseEntity.ok(new ApiMessageResponse(response));
    }

   @GetMapping("/messages/{otherEmail}")
    public ResponseEntity<List<ChatMessageResponse>> getConversation(
            @PathVariable String otherEmail,
            Authentication authentication
    ) {
        String myEmail = authentication.getName();
        return ResponseEntity.ok(rmiClientService.getConversation(myEmail, otherEmail));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserSummaryResponse>> getUsers(Authentication authentication) {
        String myEmail = authentication.getName();
        return ResponseEntity.ok(rmiClientService.listUsers(myEmail));
    }
}