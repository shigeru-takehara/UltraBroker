# UltraBroker
It is a ultra simple but powerful micro-service broker.
Simply put, if the broker can the following things.
- we can run multiple workers.
- we can control the number of same instance of workers based on the quality of requests.
- we can manage workers.
- it's secure.
- It's easy to register and de-register workers.

There is MajorDomo protocol (https://rfc.zeromq.org/spec/7/). It's very good capability, but actual implementation would be complex, and "heatbeat" is too chatty in TCP/IP communication. In Java, there is very stable Servlet container, that is in a way similar to handle servlet object while the MajoDomo handles workers. Combining the concept of the MajoDomo and maturity and stability of the Servlet container, we could build a broker that would does like the Major protocol, which is this UltraBroker.
