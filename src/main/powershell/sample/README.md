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

`<HTML><p>PS1=Id:PSAP, CommandLine1:c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample\PowerShellApp.bat, CommandLine2:{PORT_NUMBER}, WorkingDirectory:c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample</p></HTML>`

**Step 3: Run the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/Broker      |   Call the Broker. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-id | PSP | Worker ID run |
| Key-request | a string value | Request data |

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-Broker-PSP.PNG "Calling Broker Postman")

If you have many access, setting workerRefreshCount variable is recommended. The value should be 90. Based on experience, the more than 97 acess makes socket port communication stop working. This does not occur on Java application, and it occurs on Powershell application. The cause of issue has not been found. If we set the workerRefreshCount, it runs good.

**Step 4: Stop the worker.**

Call RegisterWorker again with different parameters as follows:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-Stop.PNG "WorkerRegister Stop Postman")


Go back to "How to run UltraBroker server": https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/README.md.
