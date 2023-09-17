package ultrabroker.service.support;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessManager {
  private static Map<String, WorkerInfo> workerInfoMap = new ConcurrentHashMap<>();
  private static Map<String, ProcessStore> workerProcessStoreMap = new ConcurrentHashMap<>();
  private static ProcessManager singletonsProcessManager;
  
  public static boolean CloseServletContainer = false;

  private ProcessManager() {
    super();
  }

  public static ProcessManager getProcessManager() {
    if (singletonsProcessManager == null) {
      singletonsProcessManager = new ProcessManager();
    }
    return singletonsProcessManager;
  }

  public Map<String, WorkerInfo> getWorkerInfoMap() {
    return workerInfoMap;
  }

  protected Map<String, ProcessStore> getWorkerProcessStoreMap() {
    return workerProcessStoreMap;
  }
  
  public synchronized ProcessObject getProcessObject(String workerId) throws IOException {
    ProcessStore processStore = getWorkerProcessStoreMap().get(workerId);
    if (processStore == null) {
      processStore = new ProcessStore(this.getWorkerInfoMap().get(workerId));
      this.getWorkerProcessStoreMap().put(workerId, processStore);
    }
    return processStore.getInactiveProcessObject();
  }
  
  public ProcessObject createProcessObject(String workerId) throws IOException {
    ProcessStore processStore = getWorkerProcessStoreMap().get(workerId);
    return processStore.createProcessObject();
  }
  
  public void stopAllWorkers() {
    Iterator<ProcessStore> it = this.getWorkerProcessStoreMap().values().iterator();
    while (it.hasNext()) {
      ProcessStore itObj = it.next();
      itObj.stopAllWorkers();
    }
  }
  
  public void kilAllWorkers() {
    Iterator<ProcessStore> it = this.getWorkerProcessStoreMap().values().iterator();
    while (it.hasNext()) {
      ProcessStore itObj = it.next();
      itObj.killAllWorkers();
    }
  }
  
  public void stopWorker(String workerId) {
    this.getWorkerProcessStoreMap().get(workerId).stopAllWorkers();
  }
  
  public void registerWorker(WorkerInfo workerInfo) {
    if (workerInfo.validate()) {
      getWorkerInfoMap().put(workerInfo.getId(), workerInfo);
      ProcessStore processStore = getWorkerProcessStoreMap().get(workerInfo.getId());
      if (processStore != null) {
        processStore.setWorkerInfo(workerInfo);
      }
    }
  }

  public WorkerInfo getWorkerInfo(String workerId) {
    return getWorkerInfoMap().get(workerId);
  }

}
