package ultrabroker.service.support;

public class WorkerConfigurationProperties {
  protected static int EXCESS_WORKER_CHECKING_ACCESS_COUNT = 1000; // Access count for excess worker checking 
  protected static int RETRY_COUNT = 600; // 600 times 
  protected static int RETRY_MILLISECONDS = 100;
  protected static int WORKER_COUNT_MAX = 5;
  protected static int WORKER_REFRESH_COUNT = 100;

  private int excessWorkerCheckingAccessCount = 0;
  private int retryCount = 0;
  private int retryMilliSeconds = 0;
  private int workerCountMax = 0;
  private int workerRefreshCount = 0;
  private boolean enableWorkerRefresh = false;
  
  public int getExcessWorkerCheckingAccessCount() {
    if (excessWorkerCheckingAccessCount == 0)
      return EXCESS_WORKER_CHECKING_ACCESS_COUNT;
    return excessWorkerCheckingAccessCount;
  }
  public void setExcessWorkerCheckingAccessCount(int val) {
    this.excessWorkerCheckingAccessCount = val;
  }
  public int getRetryCount() {
    if (retryCount == 0)
      return RETRY_COUNT;
    return retryCount;
  }
  public void setRetryCount(int retryCount) {
    this.retryCount = retryCount;
  }
  public int getRetryMilliSeconds() {
    if (retryMilliSeconds == 0)
      return RETRY_MILLISECONDS;
    return retryMilliSeconds;
  }
  public void setRetryMilliSeconds(int retryMilliSeconds) {
    this.retryMilliSeconds = retryMilliSeconds;
  }
  public int getWorkerCountMax() {
    if (workerCountMax == 0)
      return WORKER_COUNT_MAX;
    return workerCountMax;
  }
  public void setWorkerCountMax(int workerCountMax) {
    this.workerCountMax = workerCountMax;
  }
  public int getWorkerRefreshCount() {
    if (workerRefreshCount == 0)
      return WORKER_REFRESH_COUNT;
    return workerRefreshCount;
  }
  public void setWorkerRefreshCount(int workerRefreshCount) {
    this.workerRefreshCount = workerRefreshCount;
  }
  public boolean isEnableWorkerRefresh() {
    return enableWorkerRefresh;
  }
  public void setEnableWorkerRefresh(boolean enableWorkerRefresh) {
    this.enableWorkerRefresh = enableWorkerRefresh;
  }

}
