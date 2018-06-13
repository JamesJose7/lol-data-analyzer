# lol-data-analyzer
Spring web application that analyzes and stores League of Legends data provided by riot games api.


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
