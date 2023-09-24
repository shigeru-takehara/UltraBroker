# How to run Node JS Application

How to run sample Node JS applcation is very much the same as the sample Java application.

**Step 1 Register the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/WorkerRegister      |   WorkerRegister API where we can register a worker process. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-workerId | NJA1 | Worker ID (You can choose any word) |
| Key-commandLine1 | node | Assume Node JS has been installed. Also TypeScript is installed within Node JS.|
| Key-commandLine2 | NodeJSApp1.js | It's a generated JavaScript file. |
| Key-commandLine2 |  {PORT_NUMBER} | Socket port number, which will be assigned by a Broker object |
| Key-workingDirectory | C:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\nodejs\sample | The directory where the JavaScript file is located|

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-NJA1.PNG "WorkerRegister NodeJS Postman")


**Step 2: Check the worker registration.**

Use GET method and call http://localhost:8080/UltraBroker1/WorkerReporter, which should return something like:

`<HTML><p>NJA1=Id:NJA1, CommandLine1:node, CommandLine2:NodeJSApp1.js, CommandLine3:{PORT_NUMBER}, WorkingDirectory:C:\Users\STAKEHAR\eclipse-workspace\UltraBroker1\src\main\nodejs\sample, excessWorkerCheckingAccessCount:500, retryCount:600, retryMilliSeconds:100, workerCountMax:5, workerRefreshCount:160, enableWorkerRefresh:true</p></HTML>`

**Step 3: Run the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/Broker      |   Call the Broker. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-id | NJA1 | Worker ID run |
| Key-request | a string value | Request data |

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-Broker-NJA1.PNG "Calling Broker Postman")

If you have many access, setting workerRefreshCount variable is recommended. The value should be 160. Based on experience, the more than 164 acess makes socket port communication stop working. This does not occur on Java application, and it occurs on NodeJS application. The cause of issue has not been found. If we set the workerRefreshCount, it runs good.

**Step 4: Stop the worker.**

Call RegisterWorker again with different parameters as follows:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-Stop.PNG "WorkerRegister Stop Postman")


Go back to "How to run UltraBroker server": https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/README.md.
