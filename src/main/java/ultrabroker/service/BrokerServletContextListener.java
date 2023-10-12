package ultrabroker.service;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ultrabroker.service.support.ProcessManager;
import ultrabroker.util.TimeUtil;

public class BrokerServletContextListener extends WorkerRegister implements ServletContextListener {

  private static final long serialVersionUID = 1L;

    @Override
    public void contextDestroyed(ServletContextEvent event) {
      ProcessManager.CloseServletContainer = true;
      
      TimeUtil.waitForSeconds(1);

      stopAllWorkers();
      
      TimeUtil.waitForSeconds(1);
      
      stopAllWorkers();
      
      System.out.println("Web application is shutting down. All workers are terminated.");
    }
    
    private void stopAllWorkers() {
      try {
        this.getProcessManager().stopAllWorkers();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    }
}
