package main.websocket;

public class initBuilder {

    public init initBuilder(String flow,String channel,String phoneNumber) {

        return init.builder().flow(flow)
                .channel(channel)
                .phoneNumber(phoneNumber)
                .build();
    }
}