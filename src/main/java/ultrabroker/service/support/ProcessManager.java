package ultrabroker.service.support;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ultrabroker.net.MessageExchangeServer;

public class ProcessManager {
  private static Map<Long, Map<String, ProcessObject>> processObjectMap = new ConcurrentHashMap<>();
  private static Map<String, WorkerInfo> workerInfoMap = new ConcurrentHashMap<>();
  private static ProcessManager singletonsProcessManager;

  private ProcessManager() {
    super();
  }

  public static ProcessManager getProcessManager() {
    if (singletonsProcessManager == null) {
      singletonsProcessManager = new ProcessManager();
      return singletonsProcessManager;
    }
    return singletonsProcessManager;
  }

  protected Map<Long, Map<String, ProcessObject>> getProcessObjectMap() {
    return processObjectMap;
  }

  public Map<String, WorkerInfo> getWorkerInfoMap() {
    return workerInfoMap;
  }

  /**
   * 
   * @param threadId Broker servlet's thread Id
   * @param workerId worker Id
   * @param process  worker process
   * @param socket   server socket
   */
  public void setWorkerProcAndBrokerServer(long threadId, String workerId, Process process,
      MessageExchangeServer server) {
    ProcessObject po = new ProcessObject(process, server);
    Map<String, ProcessObject> poMap = getProcessObjectMap().get(threadId);
    if (poMap == null) {
      poMap = new ConcurrentHashMap<>();
      getProcessObjectMap().put(threadId, poMap);
    }
    poMap.put(workerId, po);
  }

  public ProcessObject getWorkerProcAndBrokerServer(long threadId, String workerId) {
    try {
      return getProcessObjectMap().get(threadId).get(workerId);
    } catch (NullPointerException e) {
      return null;
    }
  }

  public Process startWorker(String workerId, int port) throws IOException {
    WorkerInfo workerInfo = this.getWorkerInfo(workerId);

    ProcessBuilder processBuilder = new ProcessBuilder(workerInfo.getCommandStringListWithPortNumber(port));

    processBuilder.directory(new File(workerInfo.getWorkingDirectory()));
    Process process = processBuilder.start();

    return process;
  }

  public void stopAllWorkers() throws IOException {
    Iterator<Map<String, ProcessObject>> it = processObjectMap.values().iterator();
    while (it.hasNext()) {
      Map<String, ProcessObject> itObj = it.next();
      Iterator<ProcessObject> it2 = itObj.values().iterator();
      while (it2.hasNext()) {
        ProcessObject it2Obj = it2.next();
        MessageExchangeServer server = it2Obj.getBrokerServer();
        server.sendCloseRequest();
        server.close();
      }
      itObj.clear();
    }
    processObjectMap.clear();
  }

  public void registerWorker(WorkerInfo workerInfo) {
    if (workerInfo.validate()) {
      getWorkerInfoMap().put(workerInfo.getId(), workerInfo);
    }
  }

  public WorkerInfo getWorkerInfo(String workerId) {
    return getWorkerInfoMap().get(workerId);
  }

  public void destroy(long threadId) {
    Map<String, ProcessObject> poMap = getProcessObjectMap().get(threadId);
    if (poMap == null)
      return;

    for (ProcessObject po : poMap.values()) {
      po.getBrokerServer().sendCloseRequest();
    }
  }
}
