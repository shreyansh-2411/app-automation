package main.serialisationobjects;
import java.util.List;

public class bearerToken {
    private String connection = "sms";
    private List<String> resources = List.of("rario.com");
    private String responseType = "token";
    private String channel = "sms";
    private String username;
    private String verificationCode = "999999";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
