# Worker Registration

In order to run a worker, we need to register the worker to UltraBroker. This can be done by calling WorkerRegister API. The following POST parameters are used.

| Key        | Description           | Example Value  |
| ------------- |:-------------:| -----:|
| workerId      | Worker ID; it's a short string value that uniquely identifies the worker. This ID value could be used in a part of URL in the future. | "SJA1", "PS1", etc. |
| commandLine1 | These are command lines to execute the worker. One of commandLines must include "{PORT_NUMBER}" so that UlraBroker can pass the communication socket port.  | "C:/java/jdk-17/bin/java" |
| commandLine2 |  |  |
| commandLine3 |  | |
| commandLine4 | |  |
| commandLine5 |  |  |
| commandLine6 |  |  |
| commandLine7 |  | |
| commandLine8 | |  |
| commandLine9 |  | |
| commandLine10 | |  |
| workingDirectory | C:/UltraBroker1 | The directory where SJA1.jar file is located|
| excessWorkerCheckingAccessCount |  |  |
| retryCount |  |  |
| retryMilleSeconds |  |  |
| workerCountMax |  |  |
| workerRefreshCount |  |  |
| enableWorkerRefresh |  |  |
