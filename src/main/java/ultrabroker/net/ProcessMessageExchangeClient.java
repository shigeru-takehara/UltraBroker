package ultrabroker.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessMessageExchangeClient extends ProcessMessageExchangeBase {
  private BufferedReader inputReader;
  
  public ProcessMessageExchangeClient() {
    inputReader = new BufferedReader(new InputStreamReader(System.in));
  }
  
  public String getServerRequest() throws IOException {
    return inputReader.readLine();
  }

  public void replyResponse(String resp) {
    System.out.println(resp + MESSAGE_DELIMITER);
  }
  
  public void processMessage(IClientProcessMessageProcessor clientProcessMessageProcessor) {
    try {
      String inputData;

      StringBuilder message = new StringBuilder();
      while ((inputData = this.getServerRequest()) != null) {
        if (inputData.equalsIgnoreCase(ProcessMessageExchangeClient.MESSAGE_END_OF_PROCESS)) {
          break;
        }
        if (ProcessMessageExchangeClient.MESSAGE_DELIMITER.equals(inputData)) {
          this.replyResponse(clientProcessMessageProcessor.processRequest(message.toString()));
          message = new StringBuilder();
          continue;
        }
        message.append(inputData).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
