# How to run Powershell Application
Please make sure you can run Powershell script. Usually follwoing command will allow you to run local Powerscript files.

`Set-ExecutionPolicy RemoteSigned` in the Command Window.

For more inforamtion, read https://www.makeuseof.com/enable-script-execution-policy-windows-powershell/

How to run sample Powershell script applcation is very much the same as that of sample Java application.

**Step 1 Register the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/WorkerRegister      |   WorkerRegister API where we can register a worker process. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-workerId | PS1 | Worker ID (You can choose any word) |
| Key-commandLine1 | c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample\PowerShellApp.bat | The batch file that contains execution of Powershell script file. |
| Key-commandLine2 |  {PORT_NUMBER} | Socket port number, which will be assigned by a Broker object |
| Key-workingDirectory | c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample | The directory where the batch file is located|

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-PS1.PNG "WorkerRegister Powerscript Postman")


**Step 2: Check the worker registration.**

Use GET method and call http://localhost:8080/UltraBroker1/WorkerReporter, which should return something like:

`<HTML><p>PS1=Id:PS1, CommandLine1:c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample\PowerShellApp.bat, CommandLine2:{PORT_NUMBER}, WorkingDirectory:c:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\powershell\sample</p></HTML>`

**Step 3: Run the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/Broker      |   Call the Broker. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-id | PS1 | Worker ID run |
| Key-request | a string value | Request data |

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-Broker-PS1.PNG "Calling Broker Postman")



**Step 4: Stop the worker.**

Call RegisterWorker again with different parameters as follows:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-Stop.PNG "WorkerRegister Stop Postman")



