package main.websocket;

import main.nativeapp.core.LogsManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

public class Client extends WebSocketClient {
    SocketServiceData dataContext;

    public Client(URI serverUri) {
        super(serverUri);
    }

    public Client(SocketServiceData dataContext) throws URISyntaxException {
        super(new URI(dataContext.URI));
        this.dataContext=dataContext;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        LogsManager.getLogger().log(Level.INFO,"Web Socket Connection is open - "+dataContext.URI);
    }

    @Override
    public void onMessage(String message) {
        dataContext.messageList.add(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // TODO document why this method is empty
    }

    @Override
    public void onError(Exception ex) {
        LogsManager.getLogger().log(Level.WARNING,"Error in connection "+ex.getMessage());
    }
}