package main.websocket;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class init {


    private String channel;
    private String flow;
    private String phoneNumber;




}