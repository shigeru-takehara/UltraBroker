# How to Define HTTP Based Application as Worker
HTTP based applications such as Node JS based, J2EE based, CGI based, and other HTTP based applications can be defined in the UltraBroker as workers. When the multiple applications are defined, it will be executed in round robin. 

## Step 1: Register HTTP based applications

| Object        | Value           | Description  |
| ------------- |:-------------:| -----:|
| Method      | POST | HTTP Method |
| URL      | http://localhost:8080/UltraBroker1/WorkerRegister      |   WorkerRegister API where we can register a worker process. |
| Body | x-www-form-urlencoded      |    It's a type of Post Content (It will be Key/Vaue pair |
| Key-workerId | HTTP-YAHOO | Worker ID (You can choose any word with prefix "HTTP-") |
| Key-commandLine1 | 2 | The number of active URLs |
| Key-commandLine2 | https://yahoo.com | URL 1 |
| Key-commandLine3 | https://yahoo.com |URL 2 |
| Key-commandLine4 | https://yahoo.com |URL 3 |
There are three URLs defined, but it uses 2 URLs because the number of active URLs is 2.

Sample Postman:

![alt text](https://github.com/shigeru-takehara/UltraBroker/blob/main/images/Postman-WorkerRegister-HTTP.PNG "WorkerRegister Postman")

After it is executed, Response message "Worker registration is success." should be returned.

Use GET method and call http://localhost:8080/UltraBroker1/WorkerReporter, which should return something like:

`<HTML><p>HTTP-YAHOO=Id:HTTP-YAHOO, CommandLine1:2, CommandLine2:https://yahoo.com, CommandLine3:https://yahoo.com, WorkingDirectory:Not Used, excessWorkerCheckingAccessCount:1000, retryCount:600, retryMilliSeconds:100, workerCountMax:5, workerRefreshCount:100, enableWorkerRefresh:false</p></HTML>`

WorkingDirectory, excessWorkerCheckingAccessCount, retryCount, retryMilliSeconds, workerCountMax, workerRefreshCount, and enableWorkerRefresh are not used.



