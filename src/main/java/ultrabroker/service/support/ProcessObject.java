package ultrabroker.service.support;

import ultrabroker.net.MessageExchangeServer;

public class ProcessObject {
  private Process process;
  private MessageExchangeServer brokerServer;

  public ProcessObject(Process process, MessageExchangeServer server) {
    this.process = process;
    this.brokerServer = server;
  }

  public Process getProcess() {
    return process;
  }

  public void setProcess(Process process) {
    this.process = process;
  }

  public MessageExchangeServer getBrokerServer() {
    return brokerServer;
  }

  public void setServerSocket(MessageExchangeServer server) {
    this.brokerServer = server;
  }

}
