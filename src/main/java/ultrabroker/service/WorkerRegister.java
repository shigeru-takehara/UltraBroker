package ultrabroker.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ultrabroker.service.support.ProcessManager;
import ultrabroker.service.support.WorkerInfo;

@WebServlet("/WorkerRegister")
public class WorkerRegister extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static ProcessManager processManager = ProcessManager.getProcessManager();

  protected ProcessManager getProcessManager() {
    return processManager;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String workerReset = request.getParameter("workerReset");
    if ("true".equals(workerReset)) {
      this.getProcessManager().stopAllWorkers();
      response.getWriter().append("Reset all workers.");
      return;
    }

    String workerId = request.getParameter("workerId");
    String commandLine1 = request.getParameter("commandLine1");
    String commandLine2 = request.getParameter("commandLine2");
    String commandLine3 = request.getParameter("commandLine3");
    String commandLine4 = request.getParameter("commandLine4");
    String commandLine5 = request.getParameter("commandLine5");
    String commandLine6 = request.getParameter("commandLine6");
    String commandLine7 = request.getParameter("commandLine7");
    String commandLine8 = request.getParameter("commandLine8");
    String commandLine9 = request.getParameter("commandLine9");
    String commandLine10 = request.getParameter("commandLine10");
    String workingDirectory = request.getParameter("workingDirectory");

    WorkerInfo workerInfo = new WorkerInfo();
    workerInfo.setId(workerId);
    workerInfo.setCommandStringList(commandLine1, commandLine2, commandLine3, commandLine4, commandLine5,
                                    commandLine6, commandLine7, commandLine8, commandLine9, commandLine10);
    workerInfo.setWorkingDirectory(workingDirectory);

    this.getProcessManager().registerWorker(workerInfo);

    response.getWriter()
        .append(workerInfo.validate() ? "Worker registration is success." : "Worker registration failed.");
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
