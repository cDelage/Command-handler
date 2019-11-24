# README
## TMA Application install guide

### Docker install : 


Step 1 create docker image : 

	$ docker run --name mysql-tma -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.6

Step 2 start container : 

	$ docker start mysql-tma

Step 3 launch exec : 

	$ docker exec -ti mysql-tma bash

Step 4 open mysql command : 
	
	mysql --password
	root

Step 5 Create user and database : 
	
	CREATE USER 'tma'@'%' IDENTIFIED BY 'tma';
	UPDATE mysql.user SET Password=PASSWORD('tma') WHERE User='tma' AND Host='localhost';
	FLUSH PRIVILEGES;
	GRANT USAGE ON * . * TO 'tma'@'%' IDENTIFIED BY 'tma';
	GRANT USAGE ON * . * TO 'tma'@'localhost' IDENTIFIED BY 'tma';
	CREATE DATABASE IF NOT EXISTS `tma` ;
	GRANT ALL PRIVILEGES ON `tma` . * TO 'tma'@'%';
	
### Tomcat install : 

To be completed

### Application deployment:

Step 1 Start mysql-tma container :

	$ docker start mysql-tma

Step 2 Build maven project :

Build the maven project to generate the .war file.

Step 3 Tomcat WAR deploy :

Go to this URL: http://localhost:8080/manager/

In the section "Deploy", then set : 
- *Context path* = **/tma**
- *URL to WAR or directory* = (full path to the .war file in the target directory of the project)

Click on deploy

You can test the application with this URL for example :

http://localhost:8080/tma/rest/MonitorWebService

The application is now ready to use !


