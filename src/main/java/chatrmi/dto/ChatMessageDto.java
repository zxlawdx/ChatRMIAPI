package chatrmi.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String senderEmail;
    private String receiverEmail;
    private String message;
    private LocalDateTime sentAt;

    public ChatMessageDto() {
    }

    public ChatMessageDto(
            String id,
            String senderEmail,
            String receiverEmail,
            String message,
            LocalDateTime sentAt
    ) {
        this.id = id;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.message = message;
        this.sentAt = sentAt;
    }

    public String getId() {
        return id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}