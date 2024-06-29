package main.websocket;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class MatchmakingSuccess {

    String eventType;

    Data data;

}