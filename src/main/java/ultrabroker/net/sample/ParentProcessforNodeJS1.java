package ultrabroker.net.sample;

import java.io.IOException;

import ultrabroker.net.Message;
import ultrabroker.net.ProcessMessageExchangeServer;

public class ParentProcessforNodeJS1 {
  public static void main(String[] args) throws IOException {
    String[] command = { "node", "NodeJSProcessApp1.js" };
    String workingDir = "C:\\Users\\STAKEHAR\\eclipse-workspace\\UltraBroker1\\src\\main\\nodejs\\sample";

    ProcessMessageExchangeServer server = new ProcessMessageExchangeServer(command, workingDir, null);

    server.sendRequest("Hello from server 1.\nabc\n1234567890\nthis is a pen.");
    Message childOutput = server.getResponse();
    System.out.println("Child process output: " + childOutput);

    server.sendRequest("Hello from server 11.");
    childOutput = server.getResponse();
    System.out.println("Child process output: " + childOutput);

    server.sendRequest("Hello from server 111.");
    childOutput = server.getResponse();
    System.out.println("Child process output: " + childOutput);

    server.sendCloseRequest();

    server.close();
  }
}
