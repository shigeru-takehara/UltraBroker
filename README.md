# UltraBroker
It maybe
 - ultra simple
 - ultra easy
 - ultra powerful
 - ulra economical
   
  micro-service broker.

Simply put, if the broker can the following things.
- we can run multiple workers.
- we can control the number of same instance of workers based on the quality of requests.
- we can manage workers.
- it's secure.
- It's easy to register and de-register workers.

This broker is inspired by MajorDomo protocol (https://rfc.zeromq.org/spec/7/). 

In Java, there is very stable Servlet container, that is in a way similar to handle servlet objects while the MajoDomo handles workers. Combining the concept of the MajoDomo and maturity and stability of the Servlet container, we could build a broker that would does like the Major protocol, which is this UltraBroker.
