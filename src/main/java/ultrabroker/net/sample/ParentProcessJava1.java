package ultrabroker.net.sample;

import java.io.IOException;

import ultrabroker.net.Message;
import ultrabroker.net.ProcessMessageExchangeServer;

public class ParentProcessJava1 {
  public static void main(String[] args) throws IOException {
    String javaCommand = "C:/java/jdk-17/bin/java";
    String[] command = {javaCommand, "-jar", "ChildProcess.jar"}; // executable jar
    String workingDir = "C:\\Users\\STAKEHAR\\eclipse-workspace\\UltraBroker.net";

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

