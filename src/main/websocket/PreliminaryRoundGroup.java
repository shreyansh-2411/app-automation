package main.websocket;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PreliminaryRoundGroup {


    int count;

    int rounds;

    List<String> roundNames;

    List<Integer> roundWeightages;

    int totalCoinsToStake;

    int roundDurationInSeconds;

    int bufferDurationInSeconds;

    int timeBetweenRoundsInSeconds;

    int bufferDurationForFirstRoundInSeconds;


}