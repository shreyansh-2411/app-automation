package main.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class quitGame {
    private String action;
    private gameQuitPayload payload;
}