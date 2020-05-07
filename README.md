# README #

## What this project is about ##
This is an web application that allows user to publish articles and to download them in different format including HTML, JSON and XML format.

##Technology and Frameworks being used in this project ##
1. Spring Boot has the charge of managing server side code
2. Spotify docker-client has been used to communicate with docker instance
3. Maven used as build and dependency management tool
4. Template engine Thyemleaf used for HTML rendering
5. For UI design, Bootstrap and Font-awesome has been used

## Dependencies ##
1. Apache Maven(Version: 3.3.9)
2. Apache Tomcat (Version: 8)
3. JDK (Version: 1.8)


##Project Structure ##
1. /src/main/resources/ - contains client side code
2. /src/main/resources/templates/ - contains HTML code
3. /src/main/resources/static/ - contains CSS and necessary javascript code
4. /src/main/java/com/iastate/web/hydrogen - contains server-side code written in spring boot framework of java programming language
5. /hydrogen_analysis/user_program - a temporary directory containing files uploaded by user
6. /hydrogen_analysis/hydrogen_output - the result of hydrogen analysis
7. /src/main/resources/application.properties - Use this file to configure application. (Described in next section)


## Configuring application ##
To configure application, open /src/main/resources/application.properties and modify as follows:
1. docker.id: Provide running docker instance ID (You can obtain docker instance ID by running this command in terminal: docker ps)
2. hydrogen.path: Path of Hydrogen base path in docker
3. file.upload.directory: Location where uploaded files will be stored temporarily
4. hydrogen.output.directory: Location where analysis result of Hydrogen will be stored temporarily
5. docker.test.directory: A directory created in docker Hydrogen base path to move uploaded user files
6. server.port: The port at which the application will be running

 
## Deploy application in a servlet container ##
1. Go to root directory of the project
2. Open shell and type following command: ./mvnw clean package
3. Go to target directory and copy hydrogen-0.0.1-SNAPSHOT.war
4. Paste it to any servlet container. For Tomcat, paste it in webapps folder and start tomcat
5. You should be able to access application in following URL: http://localhost:9080/hydrogen/ 
6. Landing page provides a prompt to user to upload a zipped folder containing c files


## Run application with embedded tomcat ##
1. Go to root directory of the project
2. Open shell and type following command: ./mvnw clean spring-boot:run
3. Application will be deployed at default port 9080 (If available). 
4. If port 9080 is not available, you can run with port of your choice with command line arguments. For example, to run in port 9070, type: ./mvnw clean spring-boot:run -Drun.arguments="--server.port=9070"

##Instructions for uploading zip file:
1. Create a empty folder.
2. Move all c files inside that empty directory.
3. Archive the folder. It should have .zip extension.
4. Upload zipped folder in website.

## What's good about this project? ##
1. Leveraged MVC software architectural pattern; thereby, making it easily extensible, modular, transferable, and scalable.
2. Leveraged cutting-edge technologies in every front easing the potential of incorporating latest powerful features in the application. 