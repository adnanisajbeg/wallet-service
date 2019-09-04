# Wallet Service

This is a playground test project.


## Technologies

This service is done in Java + Spring Boot 2. Data is stored in H2 database.

## How to build and run

To use this project you will need:

 - Java JDK 1.8
 - Maven compatible with JDK 1.8

 In order to run the service through your IDE, navigate to ```playground.test.Application.java``` and run the Application. To run application from console, use  ```mvn spring-boot:run``` .

 In order to run all tests and create executable jar, use command ```mvn clean install``` at root folder.

 More details how to start Spring Boot application can be found on this link:

 ```https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html```

## Database

Current setup of H2 database is to save data between restarts in a file, which is set with this property (in ```application.properties```):
```
spring.datasource.url=jdbc:h2:file:./data/wallet-db
```

To access db, use this endpoint:

```
http://localhost:8080/h2
```

 ## Endpoints

By default service will start at port ```8080```. To change port, add property ```server.port = PORT_NUMBER``` in ```application.properties``` file. 

APIs that are available:

### Create player

In order to create new user, send ```POST``` request on:

```http://localhost:8080/player/add```

with body that contains username (check ```test/java/playground/test/web/PlayerControllerIntegrationTest``` for examples).

In case that username is created correctly, response will be ```"User created!"``` with ```HttpStatus 200```. 

Newly created user will have balance ```0```.

### Check balance

To get current balance of the player, send ```GET``` request on:

```http://localhost:8080/wallet?username=USERNAME_VALUE```

If given username exists in record, its balance will be returned. Otherwise error message will be given. Provided balance is of type long.

### Add funds

To add funds to player, send ```POST``` request on:

```http://localhost:8080/wallet/credit```

with body:

```{"id":"UUID","username":"USERNAME","credit":AMOUNT}```

and header:

```Content-Type: applcation/json```

Adding funds will fail if provided username is not valid player, id is not of correct format (```UUID``` format).

### Withdrawing funds

To withdraw funds, send ```POST``` request on:

```http://localhost:8080/wallet/debit```

with body:

```{"id":"UUID","username":"USERNAME","credit":AMOUNT}```


Withdrawal will fail if provided username is not valid player, id is not of correct format (```UUID``` format) and when there is not enough funds on username balance.

### Transaction history

To see transaction history for a player, send ```GET``` request on:

```http://localhost:8080/history?username=USERNAME```

If username exists, then response will contain players history. Example of one player history:

```
{
    "transactionHistoryList": [
        {
            "uuid": "72565ab8-eeee-4845-80f8-f054a780574c",
            "action": "CREDIT",
            "amount": 145,
            "status": "FAILED"
        },
        {
            "uuid": "72565ab8-aaaa-4845-80f8-f054a780574c",
            "action": "DEBIT",
            "amount": 1,
            "status": "SUCCESS"
        },
        {
            "uuid": "72565ab8-bbbb-4845-80f8-f054a780574c",
            "action": "DEBIT",
            "amount": 1,
            "status": "FAILED"
        },
        {
            "uuid": "72565ab8-cccc-4845-80f8-f054a780574c",
            "action": "DEBIT",
            "amount": 1,
            "status": "SUCCESS"
        }
    ],
    "errors": []
}
```

In case of some error during data retrial or in case of invalid username, list of errors will be returned. One such example:

```
{
    "transactionHistoryList": [],
    "errors": [
        "Invalid username!"
    ]
}
```