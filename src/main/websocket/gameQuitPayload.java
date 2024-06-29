package main.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class gameQuitPayload {
    private String contestSessionId;
    private String deviceId;
}
