# How to Setup the Project in Eclipse

Please follow the steps below:
- Assume you have Eclipse IDE and JDK version is equal to or greater than 17.
- Recommend to download Tomcat 9 or greater and install it.
- In Eclipse, select to create Dynamic Web Project (File > New > Dynamic Web Project).
- Clone or download the source code from  github.
- Copy src/main project into the Dynamic Web Project.
- Make sure the project you created uses JDK/JRE 17 or greater. You open project's property, select Java Build Path, and click Libraries tab where JRE System Library should be JavaSE-17.
- Also click Project Facets and your Java version is the same version as the previous step.
- You see many errors to correct.
- Open the project's properties and click Libraries tab. Move the Server Runtime (Apache Tomcat vXXX) to Modulepath folder and click "Apply and Close" button.
- Open module-info.java file and add requires "java.servlet;" within module BlockBroker1, which should be renamed to your project name.
- You should be able to compile and run it.
