package main.websocket;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Users {

    private String username;

    private String avatar;

    private String userId;

    private String firstName;

    boolean hasQuit;


}