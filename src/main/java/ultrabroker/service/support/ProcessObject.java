package ultrabroker.service.support;

import java.util.stream.Stream;

import ultrabroker.net.IMessageExchangeServer;

public class ProcessObject {
  private Process process;
  private IMessageExchangeServer brokerServer;
  private boolean active;
  private int execCounter;
  
  public ProcessObject(Process process, IMessageExchangeServer server) {
    this.process = process;
    this.brokerServer = server;
  }

  public Process getProcess() {
    return process;
  }

  public void setProcess(Process process) {
    this.process = process;
  }

  public IMessageExchangeServer getBrokerServer() {
    return brokerServer;
  }

  public void setBrokerServer(IMessageExchangeServer server) {
    this.brokerServer = server;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public int getExecCounter() {
    return execCounter;
  }

  public void setExecCounter(int execCounter) {
    this.execCounter = execCounter;
  }

    public void incrementExecCounter() {
      execCounter++;
    }
    
    public void resetExecCounter() {
      execCounter = 0;
    }
    
    public void killProcess() {
      Stream<ProcessHandle> phStream = this.getProcess().descendants();
      phStream.forEachOrdered(ph -> ph.destroy());
      this.getProcess().destroy();
    }
}
