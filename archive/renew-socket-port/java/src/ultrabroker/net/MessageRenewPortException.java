package ultrabroker.net;

import java.io.IOException;

public class MessageRenewPortException extends IOException {
  private int port;
  
  public void setPort(int port) {
    this.port = port;
  }
  
  public int getPort() {
    return this.port;
  }
}
