 _____           _        _____                    _           
|_   _|         | |      / ____|                  | |          
  | |  _ __   __| |_   _| |     _ __ __ ___      _| | ___ _ __ 
  | | | '_ \ / _` | | | | |    | '__/ _` \ \ /\ / / |/ _ \ '__|
 _| |_| | | | (_| | |_| | |____| | | (_| |\ V  V /| |  __/ |   
|_____|_| |_|\__,_|\__, |\_____|_|  \__,_| \_/\_/ |_|\___|_|   
                    __/ |                                      
                   |___/                                       


Table of Contents:

1. Documentation
2. Dependencies
3. Installation
4. Configuration



1. Documentation

The project's documentation is located in the directory "doc/". 
There are available class and activity diagrams, a requirement document (in Portuguese), and a JavaDoc of the project (to-do).



2. Dependencies:

The following softwares and libraries are needed to compile and run the project:
	Softwares:
	* JDK 1.7
	* NetBeans 8.0.2
	* Android Studio
	* MySQL 14.14
	* Apache Tomcat Server 8.0.22
	
	
	Libraries:
	* Crawler4J 4.1
	* JSoup 1.8.2
	* Log4J 1.2.17
	* MySQL Connection 5.1.35
	* SL4J API 1.7.12
	* SL4J-Log4J12 1.7.12
	
All libraries are included in the sub-directory "lib/".



3. Installation:

3.1 Crawler:

The crawler module is a NetBeans project, located in the sub-directory "crawler/". The project can be imported as-is. It references the jars: 
	* crawler-4j-4.1-with-dependencies.jar
	* jsoup-1.8.2.jar
	* log4j-1.2.17.jar
	* sl4j-api-1.7.12.jar
	* sl4j-log4j12-1.7.12.jar
	* IndyCrawlerUtils.jar
	
3.2 Loader:

The loader module is a NetBeans project, located in the sub-directory "loader/". The project can be imported as-is. It references the jars:
	* mysql-connector-java-5.1.35-bin.jar
	* IndyCrawlerUtils.jar
	
3.3 Utils:

The utils module is a NetBeans project, located in the sub-directory "utils/". The project can be imported as-is. It references no external jars.

3.4 Web Services:

The web services module is a NetBeans project, located in the sub-directory "web/". The project can be imported as is. It references the jars:
	* mysql-connector-java-5.1.35-bin.jar
	* IndyCrawlerUtils.jar
	
3.5 Mobile:

The mobile module is an Android Studio project located in the sub-directory "mobile/". The project can be imported as is. It references no external jars.



4. Configuration

4.1 Crawler:

The crawler modules uses two configuration files: log4j.properties and crawler.properties. The first file configures the log output, and usually does not
need to be changed.