package ultrabroker.net;

import java.io.IOException;

public interface IMessageExchangeServer {
  public void sendRequest(String val) throws IOException;
  public void sendCloseRequest() throws IOException ;
  public Message getResponse() throws IOException;
  public void close() throws IOException;
}
