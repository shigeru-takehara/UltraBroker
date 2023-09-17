# ultraBrokerNet.jar
We would like to create ultraBrakerNet.jar file that stores all Java classes under ultrabroker.net directory. Also you can find this jar file in UltraBroker1.war file in the root directory.

There is a pair of Client and Server appliation that use this library in ultrabroker.net.sample directory. You should execute both programs so that they communicate each other.

As a communication rule, the Server always sends a message first; however, you can execute the Client applcation first without any issue because the Client application waits for the Server application until it is executed.

A sample application is located in src/main/java/ulrabroker/net/sample directory. Please read https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/net/sample/README.md.

Go back to the main README.md: https://github.com/shigeru-takehara/UltraBroker/blob/main/README.md.
