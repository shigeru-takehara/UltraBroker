package ultrabroker.net.sample;

import ultrabroker.net.IClientProcessMessageProcessor;
import ultrabroker.net.ProcessMessageExchangeClient;

public class ChildProcess implements IClientProcessMessageProcessor {
  
  private int count = 0;
    
  public String processRequest(String request) {
    return "# Child process received input[" + ++count + "]: " + request;
    }
  
  
  public static void main(String[] args) {
      ProcessMessageExchangeClient client = new ProcessMessageExchangeClient();
      ChildProcess child = new ChildProcess();
      client.processMessage(child);
  }
}
