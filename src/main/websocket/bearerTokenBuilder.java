package main.websocket;

import java.util.ArrayList;
import java.util.List;

public class bearerTokenBuilder {

    public bearerToken bearerTokenBuilder(String phoneNumber,String otpCode) {
        List<String> resources = new ArrayList<String>();
        resources.add("rario.com");
        return bearerToken.builder()
                .channel("sms")
                .connection("sms")
                .responseType("token")
                .resources(resources)
                .username(phoneNumber)
                .verificationCode(otpCode)
                .build();
    }
}