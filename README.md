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

### Task 15 - User Interface-2
Create full CRUD pages for models that were used in the previous task.

### Task 16 - Data Source
Create DataSource in web-project configuration files. Switch DAO layer to lookup DataSource by JNDI name.

### Task 17 - Hibernate
Rewrite the DAO layer. Use Hibernate instead of Spring JDBC. 
The Hibernate should be used as a provider that implements JPA specification, 
the Service layer should use and depend on the JPA interfaces, not the Hibernate ones.

#### Tools that were used
- Oracle JDK (11.0.16) 
- Spring Framework (5.3.22)
- Servlet API (4.0.1)
- Hibernate (5.6.10.Final)
- Thymeleaf (3.1.0.M2)
- Tomcat (9.0.65)
- PostgreSQL (14.5) 
- H2 (2.1.214) for testing
- jUnit (5.9.0) and Mockito (4.6.1)
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

### Task 18 - Spring Boot
Convert application to Spring Boot.

### Task 19 - Spring Data JPA
Rewrite the DAO layer. Use Spring Data JPA instead of Hibernate.

### Task 20 - Validation
Add validation to your models. It could be name validation, time validation, number of students in groups, etc. 
Ask your mentor for validations that should be implemented in your code.

### Task 21 - REST
Add REST endpoints to your project. All UI functionality should be available in the REST endpoints.

### Task 22 - Swagger
Add Swagger documentation to your project. You can use 2 or 3 versions or ask your mentor.
http://localhost:8080/university/v2/api-docs
http://localhost:8080/university/swagger-ui

#### Tools that were used
- Oracle JDK (11.0.16)
- PostgreSQL (14.5)
- Spring Boot (2.7.3)
  - Spring Web
  - Spring Data JPA
  - Thymeleaf
  - PostgreSQL Driver
  - H2 Database

#### How to run this application
- Clone or download repository
- Initialize the database with `src/main/resources/create_database.sql`
- Run `UniversityApplication.main`
- Go to your browser and type `http://localhost:8080/university` in address bar
- Enjoy! 



