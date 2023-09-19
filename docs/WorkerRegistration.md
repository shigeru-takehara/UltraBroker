# Worker Registration

In order to run a worker, we need to register the worker to UltraBroker. This can be done by calling WorkerRegister API. The following POST parameters are used.

| Key        | Description           | Example Value  |
| ------------- |:-------------:| -----:|
| workerId      | Worker ID; it's a short string value that uniquely identifies the worker. This ID value could be used in a part of URL in the future. | "SJA1", "PS1", etc. |
| commandLine1 | These are command lines to execute the worker. One of commandLines must include "{PORT_NUMBER}" so that UlraBroker can pass the communication socket port.  | "C:/java/jdk-17/bin/java", "C:/sample/PowerShellApp.bat", etc. |
| commandLine2 |  |  |
| commandLine3 |  | |
| commandLine4 | |  |
| commandLine5 |  |  |
| commandLine6 |  |  |
| commandLine7 |  | |
| commandLine8 | |  |
| commandLine9 |  | |
| commandLine10 | |  |
| workingDirectory | The directory path where the worker's exe or command file is located.| |
| excessWorkerCheckingAccessCount | the number of worker access counts before checking the worker's activity. Whenever this count is met, UltraBroker decides to reduce one worker's processor so that UltraBroker manages the number of active processes of the worker. | Default value: 500   |
| retryCount | When UltraBroker receives the request, it tries to find an available worker. If maximum number of workers is running already and all workers are busy, it will retry to get an available worker. | Default value: 600  |
| retryMilliSeconds | Waiting duration before retry. | Default value: 100 |
| workerCountMax | The maximum number of process of a worker | Default value: 5  |
| workerRefreshCount | A worker is terminated and re-created after the number of access is made. | Default value: 100 |
| enableWorkerRefresh | true: refresh worker, false: no refresh | Defalut value: true |

All default values based on the number of access counts are for small applications. If you have a large number of access, we should test it before deciding the values.
The workerRefreshCount could be good if the worker needs to be refreshed (shutdown and restart). For example, our Java example does not have any issue with many accesses, we many not need to refresh workers at all; however, Powershell script example needs to be refresh in 20 to 30 counts. If it is not refreshed, it will stop working. It could be a bug, but for now we can't figure it out and this refersh works fine for many accesses.
We will provide JMeter performance testing scripts to see it.
