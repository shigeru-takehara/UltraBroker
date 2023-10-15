# How to Run Simple Java Application

If you have not deployed UltraBroker1.war onto Tomcat, please click the URL link at the bottom and deploy it first.

To run SimpleJavaApp1 application, which is located in src.main.java.ultrabroker.sample.javaapp directory, Postman is recommended to use. You can download the Postman from https://www.postman.com/downloads/.

Assuming, Tomcat is running on port 8080.

**Step 1: Register a SimpleJavaApplication.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/WorkerRegister      |   WorkerRegister API where we can register a worker process. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-workerId | SJAP | Worker ID (You can choose any word) |
| Key-commandLine1 | C:/java/jdk-17/bin/java | It's Java.exe. |
| Key-commandLine2 | -jar | one of run parameters |
| Key-commandLine3 | ChildProcess.jar |Runnable Jar, which is located in the root directory. |
| Key-workingDirectory | C:/UltraBroker1 | The directory where ChildProcess.jar file is located|

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-SJAP.PNG "WorkerRegister Postman")

After it is executed, Response message "Worker registration is success." should be returned.

**Step 2: Check the worker registration.**

Use GET method and call http://localhost:8080/UltraBroker1/WorkerReporter, which should return something like:

`<HTML><p>SJAP=Id:SJAP, CommandLine1:C:/java/jdk-17/bin/java, CommandLine2:-jar, CommandLine3:ChildProcess.jar, CommandLine4:{PORT_NUMBER}, WorkingDirectory:C:\Users\STAKEHAR\eclipse-workspace\UltraBroker.net, excessWorkerCheckingAccessCount:1000, retryCount:600, retryMilliSeconds:100, workerCountMax:5, workerRefreshCount:100, enableWorkerRefresh:false</p></HTML>`

In the above there are other default configuration variables, which are described in https://github.com/shigeru-takehara/UltraBroker/blob/main/docs/WorkerRegistration.md.

**Step 3: Run the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/Broker      |   Call the Broker. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-id | SJAP | Worker ID run |
| Key-request | a string value | Request data |

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-Broker-SJAP.PNG "Calling Broker Postman")

It returns the Response, and also it logs in C:\Temp\SimpleJavaApp1-" + port + ".txt file.

**Step 4: Stop the worker.**

Call RegisterWorker again with different parameters as follows:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-Stop.PNG "WorkerRegister Stop Postman")

Please check the C:\Temp\SimpleJavaApp1-" + port + ".txt file. you might have the content like this.

`SimpleJavaApp1 Started.`

`Client received: Hello good bye`

`Client received: Hello good bye`

`SimpleJavaApp1 Ended.`




Go back to "How to run UltraBroker server": https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/README.md

Go back to https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/README.md
