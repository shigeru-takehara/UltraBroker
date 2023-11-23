package ultrabroker.service.support;

import java.util.stream.Stream;

import ultrabroker.net.HTTPMessageExchangeServer;
import ultrabroker.net.IMessageExchangeServer;

public class ProcessObject {
  private Process process;
  private IMessageExchangeServer brokerServer;
  private boolean active;
  private int execCounter;
  private String httpMethod;
  private String pathString;
  
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

    public String getHttpMethod() {
      return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
      this.httpMethod = httpMethod;
      if (this.getBrokerServer() instanceof HTTPMessageExchangeServer) {
        ((HTTPMessageExchangeServer)this.getBrokerServer()).setHttpMethod(httpMethod);
      }
    }

    public String getPathString() {
      return pathString;
    }

    public void setPathString(String pathString) {
      this.pathString = pathString;
      if (this.getBrokerServer() instanceof HTTPMessageExchangeServer) {
        ((HTTPMessageExchangeServer)this.getBrokerServer()).setPathString(pathString);
      }
    }

}
