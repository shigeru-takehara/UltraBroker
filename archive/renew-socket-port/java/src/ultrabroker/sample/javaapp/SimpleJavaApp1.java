package ultrabroker.sample.javaapp;

import ultrabroker.net.Message;
import ultrabroker.net.MessageDoneException;
import ultrabroker.net.MessageExchangeClient;
import ultrabroker.net.MessageRenewPortException;
import ultrabroker.sample.util.FileUtil;

public class SimpleJavaApp1 {
  private MessageExchangeClient client;
  private FileUtil fileUtil;
  private String iPAddress;

  public SimpleJavaApp1(String iPAddress, int port) throws Exception {
    fileUtil = new FileUtil(
        "C:\\Temp\\SimpleJavaApp1-" + port + ".txt");
    client = new MessageExchangeClient(iPAddress, port);
    fileUtil.WriteToFile("SimpleJavaApp1 Started.");
    this.setIPAddress(iPAddress);
  }

  protected void setIPAddress(String iPAddress) {
    this.iPAddress = iPAddress;
  }
  
  protected String getIPAddress() {
    return this.iPAddress;
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
      catch (MessageRenewPortException e2) {
        fileUtil.WriteToFile("Renew port exception port=" + e2.getPort());
        client.close();
        client = new MessageExchangeClient(this.getIPAddress(), e2.getPort());
        continue;
      }
      fileUtil.WriteToFile("Client received: " + request);
      String response = "This is client response. count = " + count++;
      client.sendResponse(response);
    }
    fileUtil.WriteToFile("SimpleJavaApp1 Ended.");
  }

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.err.println("Port must be passed.");
      System.exit(0);
    }
    
    SimpleJavaApp1 client = new SimpleJavaApp1("127.0.0.1", Integer.parseInt(args[0]));

    client.execute();
  }
}
