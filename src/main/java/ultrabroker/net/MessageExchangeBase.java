package ultrabroker.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ultrabroker.util.TimeUtil;

public class MessageExchangeBase {
  public static String MESSAGE_END_OF_PROCESS = "__EOP__";
  public static String MESSAGE_RENEW_PORT_NUMBER = "__RPN__";

  private Socket socket;
  private int port;
  private String IPAddress;
  private BufferedReader reader;
  private PrintWriter writer;

  public void close() throws IOException {
    this.getReader().close();
    this.getWriter().close();
    this.getSocket().close();
  }

  protected void initializeStream() throws IOException {
    this.setReader(new BufferedReader(new InputStreamReader(this.getSocket().getInputStream())));
    this.setWriter(new PrintWriter(this.getSocket().getOutputStream(), true));
  }

  protected Socket getSocket() {
    return socket;
  }

  protected void setSocket(Socket socket) {
    this.socket = socket;
  }

  public int getPort() {
    return port;
  }

  protected void setPort(int port) {
    this.port = port;
  }

  protected String getIPAddress() {
    return IPAddress;
  }

  protected void setIPAddress(String iPAddress) {
    IPAddress = iPAddress;
  }

  public BufferedReader getReader() {
    return reader;
  }

  protected void setReader(BufferedReader reader) {
    this.reader = reader;
  }

  public PrintWriter getWriter() {
    return writer;
  }

  protected void setWriter(PrintWriter writer) {
    this.writer = writer;
  }

  protected Message readMessage() throws IOException {
    Message message = new Message();
    while (!this.getReader().ready()) {
      TimeUtil.waitForMilliSeconds(10);
    }
    while (this.getReader().ready()) {
      message.add(this.getReader().readLine());
    }
    return message;
  }

}
