package ultrabroker.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ultrabroker.service.support.ProcessManager;
import ultrabroker.service.support.ProcessObject;

@WebServlet("/Broker")
public class Broker extends WorkerRegister {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String workerId = request.getParameter("id");
    String req = request.getParameter("request");

    ProcessObject processObject = null;
    try {
      processObject = this.getProcessManager().getProcessObject(workerId);
      
      if (ProcessManager.CloseServletContainer) { // at shutdown, this if block may be required.
        return;
      }
      processObject.getBrokerServer().sendRequest(req);
      response.getWriter().append(processObject.getBrokerServer().getResponse().toString());
    }
    catch(Exception e) {
      if (processObject == null) { // possibly the above getProcessObject method returns null.
        response.getWriter().append("Please re-try.");
        e.printStackTrace();
        return;
      }
      
      processObject.killProcess();
      processObject = this.getProcessManager().createProcessObject(workerId);
    }

    if (ProcessManager.CloseServletContainer) { // at shutdown, this if block may be required.
      return;
    }
    
    processObject.setActive(false);
  }

}
