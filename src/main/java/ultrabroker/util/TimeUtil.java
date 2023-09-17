package ultrabroker.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
  public static void waitForSeconds(int val) {
      long lVal = val * 1000;
      waitForMilliSeconds(lVal);
  }

  public static void waitForMilliSeconds(long val) {
    try {
      TimeUnit.MILLISECONDS.sleep(val);
    } catch (InterruptedException ie) {}
  }
}
