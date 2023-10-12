package ultrabroker.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

public class ProcessMessageExchangeServer extends ProcessMessageExchangeBase implements IMessageExchangeServer  {
  private Process process;
  
  private BufferedWriter processInput;
  private BufferedReader processOutput;
  private BufferedReader processError;

  public ProcessMessageExchangeServer(String[] command, String workingDir, Map<String, String> environmentVariables)
      throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(command);

    processBuilder.directory(new File(workingDir));

    if (environmentVariables != null) {
      Map<String, String> processEnvironment = processBuilder.environment();
      processEnvironment.putAll(environmentVariables);
    }

    this.process = processBuilder.start();
    this.processInput = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
    this.processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    this.processError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
  }

  public void sendRequest(String input) throws IOException {
    processInput.write(input);
    processInput.newLine();
    processInput.write(MESSAGE_DELIMITER);
    processInput.newLine();
    processInput.flush();
  }

  public void sendCloseRequest() throws IOException {
    sendRequest(MESSAGE_END_OF_PROCESS);
  }

  public Message getResponse() throws IOException {
    Message message = new Message();
    String line = null;
    while ((line = processOutput.readLine()) != null) {
      if (MESSAGE_DELIMITER.equals(line)) {
        break;
      }
      message.add(line);
    }
    return message;
  }

  public String getErrorResponse() throws IOException {
    return processError.readLine();
  }

  public void sendStopSignal() throws IOException {
    sendRequest(MESSAGE_END_OF_PROCESS);
  }

  public boolean isRunning() {
    try {
      process.exitValue();
      return false; // The process has already exited
    } catch (IllegalThreadStateException e) {
      return true; // The process is still running
    }
  }

  public int waitForProcess() throws InterruptedException {
    return process.waitFor();
  }

  public Process getProcess() {
    return this.process;
  }
  
  public void close() throws IOException {
    processInput.close();
    processOutput.close();
    processError.close();
  }
}
