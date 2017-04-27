# WebCircos version 1.0 README 04/2017#

This README documents all steps necessary to get application up and running.

### What is in this document ###

* Quick summary of application
* How to install application?
* Contact

### WebCircos application ###

WebCircos is a third-tiers web based application using React.js library for the web application, the Spring Boot framework and Maven for the server which can be called the Java Based Manager. It is related to a MySQL database.
To run the application, some external tools are needed when it comes to parsing files. 

### Installing ###

**Install MySQL database and create a database.** 

Here is a link to the dowload page from the official web site: 
https://dev.mysql.com/downloads/installer/

When creating the database, store the name and password of the database. THat will be needed farther in the installation. 

**Install externals tools for parsing files.**
Caution these tools can only be run on linux and macOS. If you want to run the application on Windows you need to configure a bash compiler.  

Here is a link to a tutorial to install bash compiler : https://www.howtogeek.com/249966/how-to-install-and-use-the-linux-bash-shell-on-windows-10/

a) Install the Bedtools suite on your computer. 
Here is the link to the official installation pages : 
http://bedtools.readthedocs.io/en/latest/content/installation.html

b) Install the VCFtools suite on your computer.  
Link to the VCFtools : https://vcftools.github.io/index.html

**Clone**

Clone the repository (if you haven't done it already). Originally, repository had been placed in bitbucket.org. To clone it, we recommend to familiarize yourself with the documentation ([https://confluence.atlassian.com/bitbucket/clone-a-repository-223217891.html](Link URL)).

**Open repository in Java environment**

Originally, the application was written in The NetBeans IDE but we do not recommend it.

**Download dependencies and all necessary tools (Resolve problems).**

**Increase RAM initially allocated in your IDE. Change the garbage collector algorithm for the optimized one.**   

**Database set up**

* On the upper left of your IDE a panel containing project services and files can be found. 
* Click on services.
* Then right click on Database and select new connection. 
* Select MYSQL as the driver and then click on next. 
* Enter your database name in Database field and your database password in the Password field. 
* Put the appropriate port.  
* Test connection and if it's working click on finish. 
A url will appear with this shape will appear :"jdbc:mysql://port//your_database_name". You need to copy it.

In the /Other sources/src/main/ressources/<default package>, the application.properties file can found. 
Open it and change these following line: 

db.driver: com.mysql.jdbc.Driver
db.url:" Put the url you just copied"
db.username: "your database name"
db.password: "your database password "

**Changing the path and creating appropriate folders**

Go in the Source packages/uk.ac.cranfield.bix.services/PathFinder.java class. 
The constant path attribute value needs to be change following the place you want to store uploaded files. 
In our version file are store in the Webcircos/temp/ folder for unauthenticated user and in the Webcircos/user/ folder for authenticated user. If you want to follow the same hierachy, you need to create a *Webcircos* folder and inside it two sub-folders, one called *temp* and the other *user*   

**Starting the application**

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