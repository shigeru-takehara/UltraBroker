# How to run SimpleJavaApp1 application

If you have not deployed UltraBroker1.war onto Tomcat, please click the URL link at the bottom and deploy it first.

To run SimpleJavaApp1 application, which is located in src.main.java.ultrabroker.sample.javaapp directory, Postman is recommended to use. You can download the Postman from https://www.postman.com/downloads/.

Assuming, Tomcat is running on port 8080.

**Step 1: Register a SimpleJavaApplication.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/WorkerRegister      |   WorkerRegister API where we can register a worker process. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-workerId | SJA1 | Worker ID (You can choose any word) |
| Key-commandLine1 | C:/java/jdk-17/bin/java | It's Java.exe. |
| Key-commandLine2 | -jar | one of run parameters |
| Key-commandLine3 | SJA1.jar |Runnable Jar, which is located in the root directory. |
| Key-commandLine4 | {PORT_NUMBER} | Socket port number, which will be assigned by a Broker object |
| Key-workingDirectory | C:/UltraBroker1 | The directory where SJA1.jar file is located|

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister.PNG "WorkerRegister Postman")

After it is executed, Response message "Worker registration is success." should be returned.

**Step 2: Check the worker registration.**

Use GET method and call http://localhost:8080/UltraBroker1/WorkerReporter, which should return something like:

`<HTML><p>SJA1=Id:SJA1, CommandLine1:C:/java/jdk-17/bin/java, CommandLine2:-jar, CommandLine3:SJA1.jar, CommandLine4:{PORT_NUMBER}, WorkingDirectory:C:/Users/STAKEHAR/eclipse-workspace/UltraBroker1, excessWorkerCheckingAccessCount:500, retryCount:600, retryMilliSeconds:100, workerCountMax:20, workerRefreshCount:30, enableWorkerRefresh:true</p></HTML>`

In the above there are other default configuration variables, which are described in https://github.com/shigeru-takehara/UltraBroker/blob/main/docs/WorkerRegistration.md.

**Step 3: Run the worker.**

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/Broker      |   Call the Broker. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-id | SJA1 | Worker ID run |
| Key-request | a string value | Request data |

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-Broker.PNG "Calling Broker Postman")

It returns the Response, and also it logs in C:\Temp\SimpleJavaApp1-" + port + ".txt file.

**Step 4: Stop the worker.**

Call RegisterWorker again with different parameters as follows:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-Stop.PNG "WorkerRegister Stop Postman")

Please check the C:\Temp\SimpleJavaApp1-" + port + ".txt file. you might have the content like this.

`SimpleJavaApp1 Started.`

`Client received: Hello good bye`

`Client received: Hello good bye`

`SimpleJavaApp1 Ended.`




Go back to "How to run UltraBroker server": https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/README.md.
