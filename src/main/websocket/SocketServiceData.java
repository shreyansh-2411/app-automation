package main.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketServiceData {

    public String URI ;
    public String actualMessage;
    public String expectedMessage;
    public Map<String,String> requestHeaders=new HashMap<>();
    public List<String> messageList=new ArrayList<>();
    public int statusCode;
    public int timeOut=60;
    public int timeTaken;


}
