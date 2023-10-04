package ultrabroker.service.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import ultrabroker.net.MessageExchangeServer;
import ultrabroker.net.ProcessMessageExchangeServer;

public class ProcessStore extends ArrayList<ProcessObject> {
  private static final long serialVersionUID = 1L;
  
  private WorkerInfo workerInfo;
  private int accessCount = 0;
  private String messageExchangeType;
  
  public ProcessStore(WorkerInfo workerInfo, String messageExchangeType) {
    super();
    this.setWorkerInfo(workerInfo);
    this.setMessageExchangeType(messageExchangeType);
  }

  protected String getMessageExchangeType() {
    return messageExchangeType;
  }

  protected void setMessageExchangeType(String messageExchangeType) {
    this.messageExchangeType = messageExchangeType;
  }

  public ProcessObject getInactiveProcessObject() throws IOException {
    int size = this.size();

    for (int j=0; j<this.getWorkerInfo().getConfigurationProperties().getRetryCount(); j++) {
      for (int i=0; i<size; i++) {
        if (!this.get(i).isActive()) {
          cleanupExcessWorker(size, i);
//          System.out.println("ProcessObject Index=" + i + "  ExecCount=" + this.get(i).getExecCounter() + "  WorkerRefreshCount=" + this.getWorkerInfo().getConfigurationProperties().getWorkerRefreshCount());
          if (this.getWorkerInfo().getConfigurationProperties().isEnableWorkerRefresh() && 
              this.get(i).getExecCounter() > this.getWorkerInfo().getConfigurationProperties().getWorkerRefreshCount()) {
            System.out.println("--------Refreshing workerId=" + this.getWorkerInfo().getId());
            closeClientAndServer(this.get(i));
            this.remove(i);
            return createProcessObject();
          }
          this.get(i).setActive(true);
          this.get(i).incrementExecCounter();
//          this.renewSocket(this.get(i));
          return this.get(i);
        }
      }
      if (this.size() < this.getWorkerInfo().getConfigurationProperties().getWorkerCountMax()) {
        return createProcessObject();
      }
      try {
        TimeUnit.MILLISECONDS.sleep(this.getWorkerInfo().getConfigurationProperties().getRetryMilliSeconds());
      } catch (InterruptedException ie) {}
    }
    return null;
  }
  
//  protected void renewSocket(ProcessObject  processObject) throws IOException {
//    if (ProcessManager.CloseServletContainer) { // at shutdown, this if block may be required.
//      return;
//    }
//    
////    if (processObject.getExecCounter() > -1) {
//    if (processObject.getExecCounter() == 0) {
////      if (processObject.getExecCounter()%2 != 0) {
//        return;
//    }
//    
//    MessageExchangeServer brokerServer = new MessageExchangeServer();
//    int newPort = brokerServer.getPort();
//    processObject.getBrokerServer().sendReNewPortNumber(newPort);
//    processObject.getBrokerServer().close();
//    brokerServer.waitForClientAccepts();
//    System.out.println("renewSocket accepted");
//    processObject.setBrokerServer(brokerServer);
//  }
  
  protected void cleanupExcessWorker(int currentSize, int index) throws IOException {
    if (currentSize-1 == index) {
      return;
    }
    
    this.incrementAccessCount();
    if (this.getAccessCount() > this.getWorkerInfo().getConfigurationProperties().getExcessWorkerCheckingAccessCount()) {
      if (!this.get(currentSize-1).isActive()) {
        System.out.println("one worker closed workerId=" + this.getWorkerInfo().getId());
        this.closeClientAndServer(this.get(currentSize -1));
        this.remove(currentSize - 1);
      }
      this.initializeAccessCount();
    }
  }
  
  public void closeClientAndServer(ProcessObject processObject) throws IOException {
    processObject.getBrokerServer().sendCloseRequest();
    try {
      processObject.getBrokerServer().close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void killClientAndServer(ProcessObject processObject) throws IOException {
    this.closeClientAndServer(processObject);
    
    Stream<ProcessHandle> sp = processObject.getProcess().children();
    sp.forEach(ph -> ph.destroyForcibly());
    
    processObject.getProcess().destroyForcibly();
  }
  
  public void stopAllWorkers() throws IOException {
    this.forEach(processObject -> {
      try {
        this.closeClientAndServer(processObject);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
    this.clear();
  }

  public void killAllWorkers() throws IOException {
    this.forEach(processObject -> {
      try {
        this.killClientAndServer(processObject);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
    this.clear();
  }

  protected ProcessObject createProcessObject() throws IOException {
    if (ProcessManager.CloseServletContainer) { // at shutdown, this if block may be required.
      return null;
    }
    
    ProcessObject processObject = null;
    
    if (ProcessManager.MESSAGE_EXCHANGE_TYPE_SOCKET.equals(this.getMessageExchangeType())) {
      MessageExchangeServer brokerServer = new MessageExchangeServer();
      ProcessBuilder processBuilder = new ProcessBuilder(this.getWorkerInfo().getCommandStringListWithPortNumber(brokerServer.getPort()));

      processBuilder.directory(new File(this.getWorkerInfo().getWorkingDirectory()));
      Process process = processBuilder.start();

      processObject = new ProcessObject(process, brokerServer);
      processObject.setActive(true);
    
      brokerServer.waitForClientAccepts();
      System.out.println("createProcessObject accepted");
    }
    else if (ProcessManager.MESSAGE_EXCHANGE_TYPE_PROCESS.equals(this.getMessageExchangeType())) {
      ProcessMessageExchangeServer server = new ProcessMessageExchangeServer(this.getWorkerInfo().getCommandStringArray(), 
                                                  this.getWorkerInfo().getWorkingDirectory(), null);
      processObject = new ProcessObject(null, server);
    }
    this.add(0, processObject);

    return processObject;
  }

  protected int getAccessCount() {
    return accessCount;
  }
  
  protected void incrementAccessCount() {
    accessCount++;
  }
  
  protected void initializeAccessCount() {
    accessCount = 0;
  }
  
  protected WorkerInfo getWorkerInfo() {
    return workerInfo;
  }

  public void setWorkerInfo(WorkerInfo workerInfo) {
    this.workerInfo = workerInfo;
  }
}
