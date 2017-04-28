# WebCircos version 1.0 README 04/2017#

This README documents all steps necessary to get application up and running.

### Document content ###

* Quick summary of application
* How to install application?
* Contact

### WebCircos application ###

WebCircos is a third-tiers Web-based application using React.js library for the user interface, Spring Boot framework and Maven for the server which can be called the Java Based Manager. Files storage is based upon MySQL database.
To run the application, some external tools are needed for parsing files. 

### Installing ###

**Install and create MySQL database.** 
Official MySQL installer download page: https://dev.mysql.com/downloads/installer/

When creating the database, store the database user credentials. That will be necessary for further installation. 

**Install externals tools for parsing files.**
Caution! These tools can only be ran on Linux and MacOS. If you want to run the application on Windows you need to configure a bash compiler.  

Bash compiler installation tutorial: https://www.howtogeek.com/249966/how-to-install-and-use-the-linux-bash-shell-on-windows-10/

a) Install Bedtools suite on your computer. 
Official installation pages: http://bedtools.readthedocs.io/en/latest/content/installation.html

b) Install VCFtools suite on your computer.  
VCFtools : https://vcftools.github.io/index.html

**Clone**

Clone the repository from master branch(if you haven't done it already). Originally, repository had been placed in bitbucket.org. To clone it, we recommend to familiarize yourself with the documentation ([https://confluence.atlassian.com/bitbucket/clone-a-repository-223217891.html](Link URL)).

**Open repository in Java environment**

Originally, the application was written in The NetBeans IDE.

**Download dependencies and all necessary tools (Resolve problems).**

**Increase RAM initially allocated in your IDE. Change the garbage collector algorithm for the optimized one.**  

In the netbeans.conf file, some arguments need to be changed: 
J-Xms128, the initial size of the jvm memory.
-J-Xmx2g, the maximal size of the jvm memory
-J-XX:+UseG1GC to optimize the garbage collector. 

**Database set up**

* On the upper left panel of your IDE Projects, Services and Files tabs can be found. 
* Click on Services.
* Then right click on Database and select new connection. 
* Select MySQL as the driver and then click Next. 
* Enter your database name in Database field and your password in the Password field. 
* Enter the appropriate port.  
* Test connection and if it's working click on Finish. 
A following url will appear: "jdbc:mysql://port//your_database_name". You need to copy it.

In the subfolder Other Sources/src/main/ressources/<default package> of the project, application.properties file can be found. 
Open it and change the following lines: 

db.driver: com.mysql.jdbc.Driver
db.url:" Put the url you just copied"
db.username: "your database name"
db.password: "your database password "

**Changing the path and creating essential folders**

Go to the Source packages/uk.ac.cranfield.bix.services/PathFinder.java class of the project. 
The constant path attribute value needs to be changed following the place you want to store uploaded files. 
In our version files are stored in the Webcircos/temp/ folder for unauthenticated user and in the Webcircos/user/ folder for authenticated user. If you want to follow the same hierarchy, you need to create a *Webcircos* folder and two corresponding sub-folders inside it.   

**Deployment of the application**
ze qre using ,qven so ze need to co,pile our project specific mqven goql zhich is obtqined by follozing build plugin

```
#!xml

<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

All you need to do is to creqte q WAR file thqt zill contqin qll the dependencies:

**Starting the application**
This application uses SpringBootFramework, which creates a stand-alone instance of Tomcat WebServer.
a) 
b)

### Contact ###

* Authors: 
    + Y-H. Lo
    + R.J. Munro
    + E. Sowka
    + A. Szymanek
    + S. Weill

* Contact:
---