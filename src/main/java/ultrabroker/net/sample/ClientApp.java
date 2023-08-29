package ultrabroker.net.sample;

import ultrabroker.net.Message;
import ultrabroker.net.MessageDoneException;
import ultrabroker.net.MessageExchangeClient;

public class ClientApp {
  private MessageExchangeClient client;

  public ClientApp(String iPAddress, int port) throws Exception {
    client = new MessageExchangeClient(iPAddress, port);
  }

  public void execute() throws Exception {
    int count = 0;
    Message request;
    while (true) {
      try {
        request = client.getRequest();
      } catch (MessageDoneException e) {
        client.close();
        break;
      }
      System.out.println("Client received: " + request);
      client.setResponse(
          "This is client response. count = " + count++ + "\nThis is the second line.\nThis is the third line.");
    }
  }

  public static void main(String[] args) throws Exception {
    ClientApp client = new ClientApp("127.0.0.1", 12345);

    client.execute();
  }
}
