package ultrabroker.net.sample;

import java.io.IOException;

import ultrabroker.net.Message;
import ultrabroker.net.ProcessMessageExchangeServer;

public class ParentProcessforPowerShell1 {
  public static void main(String[] args) throws IOException {
    String[] command = {"C:\\Users\\STAKEHAR\\eclipse-workspace\\UltraBroker1\\src\\main\\powershell\\sample\\PowerShellProcessApp1.bat"};
    String workingDir = "C:\\Users\\STAKEHAR\\eclipse-workspace\\UltraBroker1\\src\\main\\powershell\\sample";

    ProcessMessageExchangeServer server = new ProcessMessageExchangeServer(command, workingDir, null);

    server.sendRequest("Hello from server 1.\nabc\nefg\n");
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

