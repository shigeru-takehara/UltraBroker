# How to run Node JS Application

How to run sample Node JS applcation is very much the same as the sample Java application.

**Step 1 Register the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/WorkerRegister      |   WorkerRegister API where we can register a worker process. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-workerId | NJAP | Worker ID (You can choose any word) |
| Key-commandLine1 | node | Assume Node JS has been installed. Also TypeScript is installed within Node JS.|
| Key-commandLine2 | NodeJSProcessApp1.js | It's a generated JavaScript file. |
| Key-workingDirectory | C:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\nodejs\sample | The directory where the JavaScript file is located|

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-NJAP.PNG "WorkerRegister NodeJS Postman")


**Step 2: Check the worker registration.**

Use GET method and call http://localhost:8080/UltraBroker1/WorkerReporter, which should return something like:

`<HTML><p>NJAP=Id:NJAP, CommandLine1:node, CommandLine2:NodeJSProcessApp1.js, WorkingDirectory:C:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\nodejs\sample, excessWorkerCheckingAccessCount:1000, retryCount:600, retryMilliSeconds:100, workerCountMax:5, workerRefreshCount:100, enableWorkerRefresh:false</p></HTML>`

**Step 3: Run the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/Broker      |   Call the Broker. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-id | NJAP | Worker ID run |
| Key-request | a string value | Request data |

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-Broker-NJAP.PNG "Calling Broker Postman")


**Step 4: Stop the worker.**

Call RegisterWorker as follows:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-Stop.PNG "WorkerRegister Stop Postman")


Go back to "How to run UltraBroker server": https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/README.md.
