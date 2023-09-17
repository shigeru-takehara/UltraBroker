package ultrabroker.service;

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

      this.getProcessManager().stopAllWorkers();
      
      TimeUtil.waitForSeconds(1);
      
      this.getProcessManager().stopAllWorkers(); // make sure queued requests are also cleared

      System.out.println("Web application is shutting down. All workers are terminated.");
    }
}
