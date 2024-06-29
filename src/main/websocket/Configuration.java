package main.websocket;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Configuration {

    int entryFee;

    int usersAllowed;

    TieBreakerRoundGroup tieBreakerRoundGroup;

    PreliminaryRoundGroup preliminaryRoundGroup;

}