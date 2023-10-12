package ultrabroker.net;

import java.io.IOException;
import java.net.Socket;

import ultrabroker.util.TimeUtil;

public class MessageExchangeClient extends MessageExchangeBase {

  public MessageExchangeClient(String iPAddress, int port) throws IOException {
    this.setPort(port);
    this.setIPAddress(iPAddress);
    this.setSocket(this.createtSocket());
    this.initializeStream();
  }

  protected Socket createtSocket() {
    Socket socket = null;
    while (true) {
      try {
        socket = new Socket(this.getIPAddress(), this.getPort());
        this.setSocket(socket);
        break;
      } catch (Exception e) {
        TimeUtil.waitForMilliSeconds(100);
      }
    }
    return socket;
  }

  public Message receiveRequest() throws IOException {
    return this.getRequest();
  }

  public Message getRequest() throws IOException {
    Message message = this.readMessage();
    String val = message.toString();
    if (val != null && val.indexOf(MESSAGE_END_OF_PROCESS) == 0) {
      throw new MessageDoneException();
    }

    if (val != null && val.indexOf(MESSAGE_RENEW_PORT_NUMBER) == 0) {
      val = val.replace(MESSAGE_RENEW_PORT_NUMBER, "");
      MessageRenewPortException exception = new MessageRenewPortException();
      exception.setPort(Integer.parseInt(val));
      throw exception;
    }

    return message;
  }

  public void sendResponse(String response) {
    this.setResponse(response);
  }

  public void setResponse(String val) {
    getWriter().println(val);
  }

}
