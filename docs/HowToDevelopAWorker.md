# How to Develop a Worker

## How to Develop a Java Worker
### Step 1 Develop a worker class that implements ultrabroker.net.IClientProcessMessageProcessor
The ultrabroker.net.IClientProcessMessageProcessor contains public String processRequest(String request) method where we should build a logic of response.
### Step 2 Instantiate ultrabroker.net.ProcessMessageExchangeClient class and worker class
Call ProcessMessageExchangeClient's processMessage method by passing the worker object.

**Example:**

```
package ultrabroker.net.sample;

import ultrabroker.net.IClientProcessMessageProcessor;
import ultrabroker.net.ProcessMessageExchangeClient;

public class ChildProcess implements IClientProcessMessageProcessor {
  
  private int count = 0;
    
  public String processRequest(String request) {
    return "# Child process received input[" + ++count + "]: " + request;
    }
  
  
  public static void main(String[] args) {
      ProcessMessageExchangeClient client = new ProcessMessageExchangeClient();
      ChildProcess child = new ChildProcess();
      client.processMessage(child);
  }
}
```


## How to Develop a Poweershell script Worker


## How to Develop a NodeJS TypeScript Worker

