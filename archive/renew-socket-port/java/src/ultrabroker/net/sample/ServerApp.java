package ultrabroker.net.sample;

import java.io.IOException;
import ultrabroker.net.Message;
import ultrabroker.net.MessageExchangeServer;
import ultrabroker.util.TimeUtil;

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

  public void renewPort() throws IOException {
    MessageExchangeServer brokerServer = new MessageExchangeServer();
    int newPort = brokerServer.getPort();
    server.sendReNewPortNumber(newPort);
    server.close();
    server = brokerServer;
    server.waitForClientAccepts();
  }
  
  public static void main(String[] args) throws Exception {
    ServerApp server = new ServerApp(12345);

    server.processRequest("First Server Request \nSecondLine  \nThird line");
    server.renewPort();
    server.processRequest("Second Server Request");
    server.renewPort();
    server.processRequest("Second Server Request 2");
    server.renewPort();
    server.processRequest("Second Server Request 3");
    server.renewPort();
    server.processRequest("Second Server Request 4");

    
    server.closeMessaging();
  }

}
