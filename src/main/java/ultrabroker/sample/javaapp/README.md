# How to run SimpleJavaApp1 application

If you have not deployed UltraBroker1.war onto Tomcat, please click the URL link at the bottom and deploy it first.

To run SimpleJavaApp1 application, which is located in src.main.java.ultrabroker.sample.javaapp directory, Postman is recommended to use. You can download the Postman from https://www.postman.com/downloads/.

Assuming, Tomcat is running on port 8080.

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


Go back to "How to run UltraBroker server": https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/README.md.
