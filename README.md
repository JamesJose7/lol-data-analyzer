# lol-data-analyzer
Spring web application that analyzes and stores League of Legends data provided by riot games api.

## Objective
We are developing an initiative at our college (Universidad Tecnica Particular de Loja, Ecuador - Loja) in which we want to create academic esports clubs so that it's also recognized as any other extracurricular activity. We are encouraging players from our college to join the already established club in order to get better at the game together and spread our goal to other provinces of our country so that we can start organizing national tournaments. The project's main objective is to provide a tool for everyone in our club and future ones to analyze historic data from their performance in the game. We will use the data provided on your API to develop performance metrics and show them in meaningful ways to the players so that they can see where they are lacking or where their strengths are. We are starting small but we hope to expand to the point where everyone can use our application. The API Methods we are using are:  Summoner-V3, League-V3, and Match-V3.

### Preview
User profile
![User profile](https://imgur.com/sGUDMCI.png)
Performance metrics (more graphs and metrics to be decided, also shown in user profile)
![User profile metrics](https://imgur.com/uGXUkBg.png)

Match history
![User matches](https://imgur.com/IC9ppx8.png)

### Run local H2 database for dev profile
Execute on the root of the project
```
java -cp h2-1.4.190.jar org.h2.tools.Server
```

## Datasources
Files to change:
- hibernate.cfg.xml
- app.properties

### Local Datasource
app.properties
```
spring.profiles.active = dev
...
```
hibernate.cfg.xml
```
...
<!-- SQL dialect -->
<!--<property name="dialect">org.hibernate.dialect.PostgreSQL94Dialect</property>-->
<property name="dialect">org.hibernate.dialect.H2Dialect</property>
...
```

### Heroku Datasource
app.properties
```
spring.profiles.active = prod
...
```
hibernate.cfg.xml
```
...
<!-- SQL dialect -->
<property name="dialect">org.hibernate.dialect.PostgreSQL94Dialect</property>
<!--<property name="dialect">org.hibernate.dialect.H2Dialect</property>-->
...
```

## Authentication

To use the admin page a username with a hashed password is now required, run this sql insertion on the DB
## Role table
```
INSERT INTO ROLE (name)
values ('ROLE_USER'),
('ROLE_ADMIN');
```
### User table
The password used for login is **'pass'**
```
INSERT INTO USER (username, password, enabled, role_id)
values ('jamesjose', '$2a$10$q0ZMt50H5RxMiue.Q9yqQOhapYLhBoR65lKnwyXnD1AtJB/o.aIWy', true, 2);
```

**TODO:** automatically insert those statements on server execution
