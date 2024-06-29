package main.websocket;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Setter
public class Rounds {

    @Getter
    int roundGroup;

    @Getter
    int roundNumber;

    @Getter
    Date startTime;

    @Getter
    Date endTime;

    @Getter
    String status;

    @Getter
    String roundWinner;

    boolean isRoundTied;

    @Getter
    List<BidSubmissions> bidSubmissions;


    public boolean getIsRoundTied() {
        return isRoundTied;
    }
}
