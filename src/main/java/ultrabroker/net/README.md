# ultraBrokerNet.jar
We would like to create ultraBrakerNet.jar file that stores all Java classes under ultrabroker.net directory.
There is a pair of Client and Server appliation that use this library. You should execute both programs so that they communicate each other.
As a communication rule, the Server always sends a message first; however, you can execute the Client applcation first without any issue because the Client application waits for the Server application until it is executed.