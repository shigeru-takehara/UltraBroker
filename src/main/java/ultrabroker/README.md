# How to run UltraBroker server

UltraBroker runs on a Servlet container. We use Apache Tomcat.


Step 1: Download Tomcat version 9 from https://tomcat.apache.org/download-90.cgi.

Step 2: Documentation is located in https://tomcat.apache.org/tomcat-9.0-doc/index.html.

Step 3: Make sure Tomcat runs. (Before running it, please make sure the followings are defined in Tomcat's conf/tomcat-users.xml has:

`<user username="admin" password="admin" roles="manager-gui"/>`
  
`<user username="robot" password="robot" roles="manager-script"/>`

Step 4: Open http://localhost:8080 to open Tomcat home page.

Step 5: Click ManagerApp button. (Enter login/password defined at Step 3.)

Step 6: Scroll down the page and find "War file to deploy" section and upload UltraBroker1.jar file, which is located in the root directory.

How to run Simple Java application: https://github.com/shigeru-takehara/UltraBroker/blob/main/src/main/java/ultrabroker/sample/javaapp/README.md.

How to run Simple Powershell appliaction: https://github.com/shigeru-takehara/UltraBroker/edit/main/src/main/powershell/sample/README.md.

Go back to the main README.md: https://github.com/shigeru-takehara/UltraBroker/blob/main/README.md.
