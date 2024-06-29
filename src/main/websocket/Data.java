package main.websocket;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter @Getter
public class Data {

    @Getter
    int id;

    @Getter
    int fkContest;

    @Getter
    String status;

    @Getter
    String winner;

    boolean isTied;

    @Getter
    String cancellationReason;

    @Getter
    Date createdAt;

    @Getter
    Date updatedAt;

    @Getter
    Contest contest;

    @Getter
    List<Users> users;

    @Getter
    List<Rounds> rounds;

    @Getter
    List<Stadiums> stadiums;

    List<Winnings> winnings;


    public boolean getIsTied() {
        return isTied;
    }

}