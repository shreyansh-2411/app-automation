package main.websocket;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class bearerToken {



    private String connection;
    private List<String> resources;
    private String responseType;
    private String channel;
    private String username;
    private String verificationCode;



}
