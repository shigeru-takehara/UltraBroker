package ultrabroker.net.sample;

import ultrabroker.net.Message;
import ultrabroker.net.MessageDoneException;
import ultrabroker.net.MessageExchangeClient;
import ultrabroker.net.MessageRenewPortException;

public class ClientApp {
  private MessageExchangeClient client;
  private String iPAddress;

  public ClientApp(String iPAddress, int port) throws Exception {
    client = new MessageExchangeClient(iPAddress, port);
    this.iPAddress = iPAddress;
  }

  public void execute() throws Exception {
    int count = 0;
    Message request = null;
    while (true) {
      try {
        request = client.getRequest();
      } 
      catch (MessageDoneException e) {
        client.close();
        break;
      }
      catch (MessageRenewPortException e2) {
        System.out.println("Renew port exception port=" + e2.getPort());
        client.close();
        client = new MessageExchangeClient(iPAddress, e2.getPort());
        continue;
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
