# Moneyxchange
- Sample application for converting currencies.

## Getting started
### Prerequisites
- [Node.js](https://nodejs.org/) (^8.10.0)
- [Angular CLI](https://cli.angular.io/) (^1.7.3)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [MySQL](https://dev.mysql.com/downloads/)

## Run the app
### Database
- Run the following script on MySQL, it can be found at: `moneyxchange/server/moneyxchange-api/src/main/resources/script.sql`
- MySQL connection properties can be modified at: `moneyxchange/server/moneyxchange-api/src/main/resources/application.properties`
- A `user` table will be created after the script execution.
- The following user will be created:

```
Username: oscar.jara
Password: admin
```

### Server (API)
Run the following and double-check the port where the server starts:

    $ cd moneyxchange/server/moneyxchange-api
    $ mvn spring-boot:run

Once done, the following URL should be used for authentication `http://localhost:8080/token/auth` *(POST)* and the user credentials should be passed in *JSON* format:

```
{
	"username": "oscar.jara",
	"password": "admin"
}
```

The authentication service will return a token that will expire after 1 hour:

```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjUiLCJzY29wZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV0sImlzcyI6Im1vbmV5eGNoYW5nZS1hcGkiLCJpYXQiOjE1MjIwNTI1MzIsImV4cCI6MTUyMjA1NjEzMn0.IN_-7j_9Mzv1WcBlZdwXqv--VP87O5On_6k13h6ZYz4"
}
```

The following URL should be used for creating another user `http://localhost:8080/signup` *(POST)* and the user data should be passed in *JSON* format:

```
{
	"username": "admin",
	"password": "admin",
	"name": "Foo",
	"lastname": "Bar"
}
```

## Client (SPA)
TBD
