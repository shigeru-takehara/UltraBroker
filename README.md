# UltraBroker - Version 0.1
It maybe
 - ultra simple
 - ultra easy
 - ultra powerful
 - ulra economical
   
  micro-service broker.

Simply put, this broker can have the following things.
- we can run multiple workers.
- we can control the number of same instance of workers.
- we can manage workers (stop, start, and refresh).
- it's secure since it's anyway Servlet container.
- Have a management GUI.

This broker is inspired by MajorDomo protocol (https://rfc.zeromq.org/spec/7/). 

In Java, there is very stable Servlet container, that is in a way similar to handle servlet objects while the MajoDomo handles workers. Combining the concept of the MajoDomo and maturity and stability of the Servlet container, we could build a broker that would does like the Major protocol, which is this UltraBroker.

We need to generate one jar file. Please read https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/net/README.md 
