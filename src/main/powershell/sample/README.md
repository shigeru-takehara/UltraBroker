# How to run Powershell Application
Please make sure you can run Powershell script. Usually follwoing command will allow you to run local Powerscript files.

`Set-ExecutionPolicy RemoteSigned` in the Command Window.

For more inforamtion, read https://www.makeuseof.com/enable-script-execution-policy-windows-powershell/

How to run sample Powershell script applcation is very much the same as the sample Java application.

**Step 1 Register the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/WorkerRegister      |   WorkerRegister API where we can register a worker process. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-workerId | PSAP | Worker ID (You can choose any word) |
| Key-commandLine1 | c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample\PowerShellApp.bat | The batch file that contains execution of Powershell script file. |
| Key-workingDirectory | c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample | The directory where the batch file is located|

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-PSAP.PNG "WorkerRegister Powerscript Postman")


**Step 2: Check the worker registration.**

Use GET method and call http://localhost:8080/UltraBroker1/WorkerReporter, which should return something like:

`<HTML><p>PSAP=Id:PSAP, CommandLine1:c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample\PowerShellProcessApp1.bat, WorkingDirectory:c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample, excessWorkerCheckingAccessCount:1000, retryCount:600, retryMilliSeconds:100, workerCountMax:5, workerRefreshCount:100, enableWorkerRefresh:false</p></HTML>`

**Step 3: Run the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/Broker/PSAP      |   Call the Broker. |
| Body | string value     |    It's a type of Post Content  |

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-Broker-PSAP.PNG "Calling Broker Postman")


**Step 4: Stop the worker.**

Call RegisterWorker as follows:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-Stop.PNG "WorkerRegister Stop Postman")


Go back to "How to run UltraBroker server": https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/README.md.
