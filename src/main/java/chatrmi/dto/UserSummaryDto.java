package chatrmi.dto;

import java.io.Serializable;

public class UserSummaryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String email;

    public UserSummaryDto() {
    }

    public UserSummaryDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}