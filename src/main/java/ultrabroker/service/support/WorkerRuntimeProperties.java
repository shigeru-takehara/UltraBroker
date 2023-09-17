package ultrabroker.service.support;

public class WorkerRuntimeProperties {
  private long accessCount;
  private float minTime;
  private float maxTime;
  private float averageTime;
  private float stdDevTime;
  public long getAccessCount() {
    return accessCount;
  }
  public void setAccessCount(long accessCount) {
    this.accessCount = accessCount;
  }
  public float getMinTime() {
    return minTime;
  }
  public void setMinTime(float minTime) {
    this.minTime = minTime;
  }
  public float getMaxTime() {
    return maxTime;
  }
  public void setMaxTime(float maxTime) {
    this.maxTime = maxTime;
  }
  public float getAverageTime() {
    return averageTime;
  }
  public void setAverageTime(float averageTime) {
    this.averageTime = averageTime;
  }
  public float getStdDevTime() {
    return stdDevTime;
  }
  public void setStdDevTime(float stdDevTime) {
    this.stdDevTime = stdDevTime;
  }
  
}
