# University

### Description
This application is the *Set of Tasks* of the **Foxminded Java Spring Mentoring Program**

## Features to do
### Task 9 - Decompose university
Analyze and decompose University(create UML class diagram for application).
You should make a research and describe university structure. 
The main feature of the application is Class Timetable for students and teachers. 
Students or teachers can get their timetable for a day or for a month.
Add png image to the separate GitLab project. 
After this task, all other tasks will be in this repository so give it a meaningful name.

### Task 10 - Models
Create a JAVA project based on the University UML class diagram from the previous task.
This task requires only models implementation but additional requirements could be provided 
by your mentor. 

### Task 11 - DAO layer
Create Spring JDBC based DAO for your application.
Use Spring JdbcTemplate.

### Task 12 - Service Layer
Create a service layer and implement business logic 
(add/remove entities to other entities and save them to DB, etc). 
A mentor can provide additional business rules.
You should use Spring IoC.

### Task 13 - Exceptions and Logging
Add custom exceptions and logging. **Use slf4j + logback**

### Task 14 - User Interface-1
Create status pages (read data from database - show it in HTML). 
Use Spring MVC and Thymeleaf, Bootstrap. 

#### Tools that were used
- Oracle JDK (11.0.15.1) 
- Spring Framework (5.3.20)
- Servlet API (4.0.1)
- Thymeleaf (3.1.0.M2)
- Tomcat (9.0.63)
- PostgreSQL (14.2) 
- H2 (2.1.212) for testing
- jUnit (5.8.2) and Mockito (4.4.0)
- Logback (1.2.11)

#### How to run this application
- Clone or download repository  
- Build `university.war` package by running `mvn clean compile war:war` in application root directory
- Initialize the database with `src/main/resources/create_database.sql`
- Run 'Tomcat 9' (how to do this is not the subject of this text)
- Copy built package from `{app_root_dir}/target/university.war` to `{tomcat_root_dir}/webapps/university.war`. 
Package will be automatically unpacked
- Go to your browser and type `http://localhost:port/university` in address bar (`port` is usually `8080`)
- Enjoy! 



