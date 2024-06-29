package main.serialisationobjects;

public class GenerateOTP {
    private String channel = "sms";
    private String flow = "SIGNINUP";
    private String phoneNumber;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFlow() {
        return flow;
    }
public String getChannel() {
        return channel;
    }

}
