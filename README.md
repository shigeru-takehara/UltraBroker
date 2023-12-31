# UltraBroker - Version 0.4 

It is:
 - ultra fast
 - ultra simple
 - ultra easy
   
  micro-service broker.

Simply put, this broker can have the following things.
- Support both processed based workers and thread based workers e.g. Spring boot apps
- we can set the maximum number of processes for a worker.
- The number of processes are automatically adjusted based on the the access volume for process based workers
- A worker configuration can be hot updated under being executed.
- we can manage workers (stop, start, and refresh). (not complete)
- it's secure since broker objects and worker processors run on the same hardware.
- Have a management GUI. (not right now)

This broker is inspired by MajorDomo protocol (https://rfc.zeromq.org/spec/7/). 

In Java, there is very stable Servlet container, that is in a way similar to handle servlet objects while the MajoDomo handles workers. Combining the concept of the MajoDomo and maturity and stability of the Servlet container, we could build a broker that would does like the Major protocol, which is this UltraBroker.

There is one important runtime evnrionment rule-Broker objects (Servets) and workers (processes) must run on the same hardware except HTTP based workers, which can be run on other servers.
If you need to run many more workers, you could run it on more hardwares with a proxy server.

The request information must be characters or string value based. The current Java and Powerscript based communication compontents can handle new lines; therefore, we can send multiple lines in a request. It is depends on how we build commuication component libraries.
Currently the following communication components are provided:

- Java
- Powershell
- NodeJS (TypeScript/JavaScript)
- Python
- Thread based or http based apps

Our development uses Tomcat servlet container. You should be able to use any servlet container other than Tomcat, but our documentation will always use the Tomcat.

- Java JDK 17 or later
- Apache Tomcat 9 or later
  
How to run UltraBroker server is described in https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/README.md.

How to develop a worker is described in https://github.com/shigeru-takehara/UltraBroker/blob/main/docs/HowToDevelopAWorker.md.

Version update information: https://github.com/shigeru-takehara/UltraBroker/blob/main/docs/Version%20Update%20Information.md
