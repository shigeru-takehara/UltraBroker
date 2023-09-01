# UltraBroker - Version 0.1
It maybe
 - ultra simple
 - ultra easy
 - ultra powerful
 - ulra economical
   
  micro-service broker.

Simply put, this broker can have the following things.
- we can run multiple workers. 
- we can control the number of same instance of workers. (not right now)
- we can manage workers (stop, start, and refresh). (not complete)
- it's secure since broker objects and worker processors run on the same hardware.
- Have a management GUI. (not right now)

This broker is inspired by MajorDomo protocol (https://rfc.zeromq.org/spec/7/). 

In Java, there is very stable Servlet container, that is in a way similar to handle servlet objects while the MajoDomo handles workers. Combining the concept of the MajoDomo and maturity and stability of the Servlet container, we could build a broker that would does like the Major protocol, which is this UltraBroker.

There is one important runtime evnrionment rule-Broker objects (Servets) and workers (processes) must run on the same hardware.
If you need to run many more workers, you could run it on more hardwares with a proxy server.

The request information must be characters or string value based. The current Java and Powerscript based communication compontents can handle new lines; therefore, we can send multiple lines in a request. It is depends on how we build commuication component libraries.

Our development uses Tomcat servlet container. You should be able to use any servlet container other than Tomcat, but our documentation will always uses the Tomcat.

- Java JDK 17
- Tomcat 9
  
We need to generate one jar file. Please read https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/net/README.md 
