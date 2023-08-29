package ultrabroker.net;

import java.io.IOException;
import java.net.ServerSocket;

public class MessageExchangeServer extends MessageExchangeBase {
  protected static final int MIN_PORT_NUMBER = 1024;
  protected static final int MAX_PORT_NUMBER = 65535;
  protected static final String ERROR_MESSAGE_PORT_NUMBER = "No available port found between ";

  private ServerSocket serverSocket;

  public MessageExchangeServer() throws IllegalStateException {
    this.setServerSocket(getAvailableServerSocket());
    synchronized (this) { // avoid multiple threads get the same port#
      this.setPort(this.getServerSocket().getLocalPort());
    }
    System.out.println("Port =" + this.getPort());
  }

  public void waitForClientAccepts() throws IOException {
    this.setSocket(this.getServerSocket().accept());
    this.initializeStream();
  }

  public MessageExchangeServer(int port) throws IOException {
    this.setPort(port);
    this.setServerSocket(new ServerSocket(port));
    this.setSocket(serverSocket.accept());
    this.initializeStream();
  }

  public void sendCloseRequest() {
    this.sendRequest(MESSAGE_END_OF_PROCESS);
  }

  public void sendRequest(String val) {
    this.setRequest(val);
  }

  public void setRequest(String val) {
    getWriter().println(val);
  }

  public Message receiveResponse() throws IOException {
    return this.getResponse();
  }

  public Message getResponse() throws IOException {
    return this.readMessage();
  }

  public void close() throws IOException {
    super.close();
    this.getServerSocket().close();
  }

  public static ServerSocket getAvailableServerSocket() {
    for (int port = MIN_PORT_NUMBER; port <= MAX_PORT_NUMBER; port++) {
      try {
        return new ServerSocket(port);
      } catch (IOException e) {
        // try the next port#
      }
    }

    throw new IllegalStateException(ERROR_MESSAGE_PORT_NUMBER + MIN_PORT_NUMBER + "-" + MAX_PORT_NUMBER);
  }

  public static int findAvailablePort() {
    for (int port = MIN_PORT_NUMBER; port <= MAX_PORT_NUMBER; port++) {
      if (isPortAvailable(port)) {
        return port;
      }
    }

    throw new IllegalStateException(ERROR_MESSAGE_PORT_NUMBER + MIN_PORT_NUMBER + "-" + MAX_PORT_NUMBER);
  }

  private static boolean isPortAvailable(int port) {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      // If binding to the port succeeds, it means the port is available.
      return true;
    } catch (IOException e) {
      // If binding fails, the port is not available.
      return false;
    }
  }

  protected ServerSocket getServerSocket() {
    return serverSocket;
  }

  protected void setServerSocket(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

}
