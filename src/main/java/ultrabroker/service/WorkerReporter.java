package ultrabroker.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ultrabroker.service.support.WorkerInfo;

@WebServlet("/WorkerReporter")
public class WorkerReporter extends WorkerRegister {
  private static final long serialVersionUID = 1L;

  public static final String REPORT_TYPE_ALL = "all";

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // String reportType = request.getParameter("reportType");

    Map<String, WorkerInfo> workerInfoMap = this.getProcessManager().getWorkerInfoMap();
    response.getWriter().append("<HTML>");
    workerInfoMap.forEach((k, v) -> {
      try {
        response.getWriter().append("<p>" + k + "=" + v + "</p>");
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
    response.getWriter().append("</HTML>");
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
