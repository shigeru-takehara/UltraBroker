package ultrabroker.net.sample;

import java.io.IOException;

import ultrabroker.net.Message;
import ultrabroker.net.ProcessMessageExchangeServer;

public class ParentProcessPython {
  public static void main(String[] args) throws IOException {
    String javaCommand = "python";
    String[] command = {javaCommand, "main.py"}; // executable jar
    String workingDir = "C:\\Users\\STAKEHAR\\eclipse-workspace\\UltraBroker1\\src\\main\\python\\sample";

    ProcessMessageExchangeServer server = new ProcessMessageExchangeServer(command, workingDir, null);

    server.sendRequest("Hello from server 1.\nabc\nefg");
    Message childOutput = server.getResponse();
    System.out.println("Child process output: " + childOutput);
    
    server.sendRequest("Hello from server 11.");
     childOutput = server.getResponse();
    System.out.println("Child process output: " + childOutput);
    
    server.sendRequest("Hello from server 111.");
     childOutput = server.getResponse();
    System.out.println("Child process output: " + childOutput);
    
   server.sendRequest("Hello from server 1111.");
    childOutput = server.getResponse();
    System.out.println("Child process output: " + childOutput);

    server.sendCloseRequest();

    server.close();
  }
}

