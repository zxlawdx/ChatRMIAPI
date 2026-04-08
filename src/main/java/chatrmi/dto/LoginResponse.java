package chatrmi.dto;

public record LoginResponse(
        String message,
        String token,
        String email
) {}