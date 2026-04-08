package chatrmi.dto;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        String id,
        String senderEmail,
        String receiverEmail,
        String message,
        LocalDateTime sentAt
) {}