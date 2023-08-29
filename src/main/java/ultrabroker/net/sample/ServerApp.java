package ultrabroker.net.sample;

import java.io.IOException;
import ultrabroker.net.Message;
import ultrabroker.net.MessageExchangeServer;

public class ServerApp {
  private MessageExchangeServer server;

  public ServerApp(int port) throws IOException {
    server = new MessageExchangeServer(port);
  }

  public void processRequest(String request) throws IOException {
    server.sendRequest(request);
    Message response = server.receiveResponse();
    System.out.println("Server received: " + response);
  }

  public void closeMessaging() throws IOException {
    server.sendCloseRequest();
    server.close();
  }

  public static void main(String[] args) throws Exception {
    ServerApp server = new ServerApp(12345);

    server.processRequest("First Server Request \nSecondLine  \nThird line");
    server.processRequest("Second Server Request");
    server.closeMessaging();
  }

}
