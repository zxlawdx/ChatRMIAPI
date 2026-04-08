package chatrmi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MessageRequest {

    @NotBlank(message = "Destinatário é obrigatório")
    @Email(message = "Email do destinatário inválido")
    private String receiverEmail;

    @NotBlank(message = "Mensagem é obrigatória")
    @Size(max = 1000, message = "Mensagem muito grande")
    private String message;

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}