package ultrabroker.service.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import ultrabroker.net.MessageExchangeServer;

public class ProcessStore extends ArrayList<ProcessObject> {
  private static final long serialVersionUID = 1L;
  
  private WorkerInfo workerInfo;
  private int accessCount = 0;
  
  public ProcessStore(WorkerInfo workerInfo) {
    super();
    this.setWorkerInfo(workerInfo);
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
  
  protected void cleanupExcessWorker(int currentSize, int index) {
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
  
  public void closeClientAndServer(ProcessObject processObject) {
    processObject.getBrokerServer().sendCloseRequest();
    try {
      processObject.getBrokerServer().close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void killClientAndServer(ProcessObject processObject) {
    this.closeClientAndServer(processObject);
    
    Stream<ProcessHandle> sp = processObject.getProcess().children();
    sp.forEach(ph -> ph.destroyForcibly());
    
    processObject.getProcess().destroyForcibly();
  }
  
  public void stopAllWorkers() {
    this.forEach(processObject -> this.closeClientAndServer(processObject));
    this.clear();
  }

  public void killAllWorkers() {
    this.forEach(processObject -> this.killClientAndServer(processObject));
    this.clear();
  }

  protected ProcessObject createProcessObject() throws IOException {
    if (ProcessManager.CloseServletContainer) { // at shutdown, this if block may be required.
      return null;
    }
    
    MessageExchangeServer brokerServer = new MessageExchangeServer();
    ProcessBuilder processBuilder = new ProcessBuilder(this.getWorkerInfo().getCommandStringListWithPortNumber(brokerServer.getPort()));

    processBuilder.directory(new File(this.getWorkerInfo().getWorkingDirectory()));
    Process process = processBuilder.start();

    ProcessObject processObject = new ProcessObject(process, brokerServer);
    processObject.setActive(true);
    
    this.add(0, processObject);
    brokerServer.waitForClientAccepts();

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
