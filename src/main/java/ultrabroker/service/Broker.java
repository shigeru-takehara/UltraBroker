package ultrabroker.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ultrabroker.net.MessageExchangeServer;
import ultrabroker.service.support.ProcessObject;

@WebServlet("/Broker")
public class Broker extends WorkerRegister {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String workerId = request.getParameter("id");
    String req = request.getParameter("request");

    long threadId = java.lang.Thread.currentThread().getId();
    ProcessObject processObject = this.getProcessManager().getWorkerProcAndBrokerServer(threadId, workerId);

    if (processObject == null) {
      MessageExchangeServer brokerServer = new MessageExchangeServer();
      Process process = this.getProcessManager().startWorker(workerId, brokerServer.getPort());
      this.getProcessManager().setWorkerProcAndBrokerServer(threadId, workerId, process, brokerServer);
      brokerServer.waitForClientAccepts();
      brokerServer.sendRequest(req);
    } else {
      processObject.getBrokerServer().sendRequest(req);
    }
    processObject = this.getProcessManager().getWorkerProcAndBrokerServer(threadId, workerId);
    response.getWriter().append(processObject.getBrokerServer().getResponse().toString());
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

  @Override
  public void destroy() {
    long threadId = java.lang.Thread.currentThread().getId();
    this.getProcessManager().destroy(threadId);
    super.destroy();
  }

}
