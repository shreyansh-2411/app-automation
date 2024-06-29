package main.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BidSubmissions {

    String userId;

    String bidValue;

    boolean isNullBid;

    boolean hasQuit;

    int cumulativePoints;

    String points;

    String rank;

    int balanceBeforeTxn;

    String balanceAfterTxn;

    public boolean getIsNullBid() {
        return isNullBid;
    }

    public boolean getHasQuit() {
        return hasQuit;
    }

}