package ultrabroker.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HTTPMessageExchangeServer extends ProcessMessageExchangeBase implements IMessageExchangeServer {

  private static List<String> roundRobin = new ArrayList<>();
  private static int roundRobinIndex = -1;
  private static int activeURLCount = -1;

  private String httpMethod;
  private String pathString;

  private ThreadLocal<String> threadLocal = new ThreadLocal<>();
  
  /**
   * 
   * @param command              [0]: the number of active instances [1] to [9]
   *                             URLs
   * @param workingDir
   * @param environmentVariables
   * @throws IOException
   */
  public HTTPMessageExchangeServer(String[] command, String workingDir, Map<String, String> environmentVariables)
      throws IOException {
    activeURLCount = Integer.parseInt(command[0]);
    for (int i = 1; i < command.length; i++) {
      if (command[i].isEmpty())
        continue;
      roundRobin.add(command[i]);
    }
  }

  protected String getURL() {
    return roundRobin.get(this.getRoundRobinIndex()) + "/" + this.getPathString();
  }

  protected synchronized int getRoundRobinIndex() {
    if (roundRobinIndex == activeURLCount - 1) {
      roundRobinIndex = 0;
    } else {
      roundRobinIndex++;
    }
//    System.out.println("RoundRobin index=" + roundRobinIndex);
    return roundRobinIndex;
  }

  public void sendRequest(String input) throws IOException {
    if ("GET".equals(this.getHttpMethod())) {
      doGet(this.getURL());
    } else if ("POST".equals(this.getHttpMethod())) {
      doPost(this.getURL(), input);
    } else if ("PUT".equals(this.getHttpMethod())) {
      doPut(this.getURL(), input);
    } else if ("DELETE".equals(this.getHttpMethod())) {
      doDelete(this.getURL());
    }
  }

  public void sendCloseRequest() throws IOException {
    // do nothing
  }

  public Message getResponse() throws IOException {
    Message message = new Message();
    message.add(threadLocal.get());
    return message;
  }

  public void close() throws IOException {
    // do nothing
  }

  protected void doGet(String externalURL) throws IOException {
    HttpURLConnection connection = openConnection(externalURL, HttpMethod.GET);
    processResponse(connection);
  }

  protected void doPost(String externalURL, String request) throws IOException {
    HttpURLConnection connection = openConnection(externalURL, HttpMethod.POST);
    writePayload(connection, request);
    processResponse(connection);
  }

  protected void doPut(String externalURL, String request) throws IOException {
    HttpURLConnection connection = openConnection(externalURL, HttpMethod.PUT);
    writePayload(connection, request);
    processResponse(connection);
  }

  protected void doDelete(String externalURL) throws IOException {
    HttpURLConnection connection = openConnection(externalURL, HttpMethod.DELETE);
    processResponse(connection);
  }

  private HttpURLConnection openConnection(String url, HttpMethod method) throws IOException {
    URL requestUrl = new URL(url);
    HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
    connection.setRequestMethod(method.toString());
    connection.setDoOutput(true); // Needed for POST and PUT requests
    return connection;
  }

  private void writePayload(HttpURLConnection connection, String payload) throws IOException {
    try (OutputStream os = connection.getOutputStream()) {
      byte[] input = payload.getBytes("utf-8");
      os.write(input, 0, input.length);
    }
  }

  private void processResponse(HttpURLConnection connection) throws IOException {
    int statusCode = connection.getResponseCode();
    if (statusCode == 200 || statusCode == 204) {
      // Successful response
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      StringBuilder content = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        content.append(line);
      }
      // Process the content as needed
      threadLocal.set(content.toString());
    } else {
      // Handle error response
      System.out.println("Request failed with response code: " + statusCode);
    }
  }

  private enum HttpMethod {
    GET, POST, PUT, DELETE
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getPathString() {
    return pathString;
  }

  public void setPathString(String pathString) {
    this.pathString = pathString;
  }

}
