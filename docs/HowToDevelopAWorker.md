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


## How to Develop a Powershell script Worker
### Step 1 Develop $customPRocessRequest function in a ps1 file where it should take $request variable and add a logic of response.
### Step 2 Call .\ProcessMessageExchangeClient.ps1 with $customProcessRequest function as follows.
```
$global:count = 0

# Example custom ProcessRequest function
$customProcessRequest = {
    param ($request)
    # Your custom processing logic here
	$global:count++
    return "Custom processing: $request " + $global:count
}

# Run MessageProcessing.ps1 with the custom ProcessRequest function
.\ProcessMessageExchangeClient.ps1 -ProcessRequest $customProcessRequest
```

## How to Develop a NodeJS TypeScript Worker
### Step 1 Develop processRequest(request: string): string function and add a logic of response.
### Step 2 Import ProcessOperation function from ./ProcessMessageExchangeClient.
### Step 3 Call ProcessOperation by passing processRequest function.

**Example:**

```
import { ProcessOperation } from './ProcessMessageExchangeClient';

function processRequest(request: string): string {
    return "!!!!client echo:" + request;
}

ProcessOperation(processRequest);
```

## How to Debug
You can use a debugger, but since workers use standard input for receiving a request, you can send your request from your console or command window. There are two special strings:
- `__DEL__`: this is the request delimitor.
- `__EOP__`: this is the process end request string.

Eaxampl:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Run%20a%20Worker%20from%20console.PNG "Run a Worker from console")
  
Go back to main readme file: https://github.com/shigeru-takehara/UltraBroker/blob/main/README.md.
