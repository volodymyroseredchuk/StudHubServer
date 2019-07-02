# StudHubServer

RESTfull application created by students of SoftServe IT Academy

## Getting Started

To start working on this project please add `application.properties` file in the root
directory of the project (next to `README.md` and `.gitignore`) containing the same
info as `application.properties` file in `src/main/resources/` directory, but 
change `${...}` values to your local credentials.


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

## Additional Info

When launching project for the first time, you have to load client and server both and allow
 your browser to work with insecure connection. To do so, when getting warning screen 
 you have to click 'show advanced' and let browser continue work with unregistered certificate.

## Authors

* **Volodymyr Oseredchuk** - *Work description* - [VolodymyrOseredchuk](https://github.com/volodymyroseredchuk)
* **Taras Hlukhovetskyi** - *Work description* - [TarasHlukhovetskyi](https://github.com/tarasgl)
* **Olha Lozinska** - *Work description* - [OlhaLozinska](https://github.com/OlhaLozinska)
* **Olena Andrushchenko** - *Work description* - [OlenaAndrushchenko](https://github.com/diru4ova)
* **Andrii Vashchenok** - *Work description* - [AndriiVashchenok](https://github.com/Zap1999)
* **Rostyslav Hlynka** - *Work description* - [RostyslavHlynka](https://github.com/Jarvizzik)
* **Danylo Lototskyi** - *Work description* - [DanyloLototskyi](https://github.com/DaNkOLULzz)
* **Andrii Dobrianskyi** - *Work description* - [AndriiDobrianskyi](https://github.com/andriydobrianskiy)
