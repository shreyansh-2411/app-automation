package main.websocket;
import java.net.URISyntaxException;
import java.util.Map;

public class WebClient {

    private final Client webSocket;

    public WebClient(SocketServiceData socketContext) throws URISyntaxException {
        webSocket = new Client(socketContext);
    }

    public void connectAndListen(SocketServiceData socketContext) throws InterruptedException{
            if (webSocket.isOpen()) {
                webSocket.send(socketContext.actualMessage);
            } else {
                if (!socketContext.requestHeaders.isEmpty()) {
                    final Map<String, String> requestHeaderParams = socketContext.requestHeaders;
                    requestHeaderParams.forEach(webSocket::addHeader);
                }
                webSocket.connectBlocking();
                webSocket.send(socketContext.actualMessage);
        }
        while (socketContext.messageList.isEmpty()) {
            Thread.sleep(5000);
        }
    }
    public void closeSocket() {
        webSocket.close();
    }

    public void send(String actualMessage) {
        webSocket.send(actualMessage);
    }
}