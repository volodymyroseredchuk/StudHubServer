# StudHubServer

RESTfull application created by students of SoftServe IT Academy

## Getting Started

To start working on this project please add `application.properties` file in the resources folder
that contains next info along with credentials for your local MySql instance and jwt token security


```
spring.datasource.url = jdbc:mysql://localhost:3306/studhub?serverTimezone=UTC
spring.datasource.username = 
spring.datasource.password = 
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
localhost.url = jdbc:mysql://localhost:3306/
script.create = create database studhub
script.drop = drop database studhub
skip.db.init = true
studhub.app.jwtSecret =
studhub.app.jwtExpiration =
```

## Running Application

To automatically create database with test data run the next command prompt
(run only once)
```
mvn clean install -D skip.db.init=false
```

To run the application
```
mvn spring-boot:run
```

To recompile the application
(run always if db is already created)
```
mvn clean install
```

## Authors

* **Volodymyr Oseredchuk** - *Work description* - [VolodymyrOseredchuk](https://github.com/volodymyroseredchuk)
* **Taras Hlukhovetskyi** - *Work description* - [TarasHlukhovetskyi](https://github.com/tarasgl)
* **Olha Lozinska** - *Work description* - [OlhaLozinska](https://github.com/OlhaLozinska)
* **Olena Andrushchenko** - *Work description* - [OlenaAndrushchenko](https://github.com/diru4ova)
* **Andrii Vashchenok** - *Work description* - [AndriiVashchenok](https://github.com/Zap1999)
* **Rostyslav Hlynka** - *Work description* - [RostyslavHlynka](https://github.com/Jarvizzik)
* **Danylo Lototskyi** - *Work description* - [DanyloLototskyi](https://github.com/DaNkOLULzz)
* **Andrii Dobrianskyi** - *Work description* - [AndriiDobrianskyi](https://github.com/andriydobrianskiy)
