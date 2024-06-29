package main.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class bidSubmissionPayload
{  private String contestSessionId;
    private String bidValue;
    private String roundNumber;
    private String roundGroup;
}
