package ultrabroker.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ultrabroker.service.support.ProcessManager;
import ultrabroker.service.support.ProcessObject;
import ultrabroker.service.support.WorkerInfo;

@WebServlet("/Broker")
public class Broker extends WorkerRegister {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    String workerId = request.getParameter("id");
//    String req = request.getParameter("request");

    String workerId = this.getWorkerId(request);
    String req = this.getRequestBody(request);
    
//    System.out.println("WorkerId =" + workerId);
    String pathString = this.getPathAfterWorkerId(request);
//    System.out.println("pathString =" + pathString);
    
    
    ProcessObject processObject = null;
    try {
      processObject = this.getProcessManager().getProcessObject(workerId);
      if (WorkerInfo.isHTTPWorker(workerId)) { //HTTPMessageExchangeServer
        processObject.setHttpMethod(request.getMethod());
        processObject.setPathString(pathString);
      }
      
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

  protected String getRequestBody(HttpServletRequest request) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }
  }
  
  protected String getWorkerId(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    String pathInfo = requestURI.substring(request.getContextPath().length());
//    System.out.println("Path Information: " + pathInfo);
    String[] pathParts = pathInfo.split("/");
    
    return pathParts[2];
  }

  protected String getPathAfterWorkerId(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    String pathInfo = requestURI.substring(request.getContextPath().length());
//    System.out.println("Path Information: " + pathInfo);
    String[] pathParts = pathInfo.split("/");
    StringBuffer result = new StringBuffer();
    for(int i=3; i<pathParts.length; i++) {
      result.append(pathParts[i]);
      if (i < pathParts.length-1) {
        result.append("/");
      }
    }
    return result.toString();
  }
}
