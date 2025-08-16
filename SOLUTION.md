# Ledger MS SOLUTION
This microservice handles a payment transfer API trigger, it is the client facing Rest API exposed according to the below implementation:

* Namespace: 'com.cypcode.ledger-service'
* Spring: '3.5.4 POM'
* Java: '17'
* Database: 'In-Memory H2'
* Architecture: 'Microservices Architecture'
* Author: 'Nkululeko Gininda'

# Rest API Interfaces
* Base Url: http://localhost:8081
* POST Create account: '/accounts'
* GET Account by ID: '/accounts/{id}'
* POST Ledger Transfer: '/ledger/transfer' 
* GET Service Health: '/actuator/health'


### Rest API Execution Steps
For detailed interface process, the flow of events:

* POST Create account: '/accounts'
  * Client trigger a new account create with payload below:
    * name
    * type
    * balance
* GET Account by ID: '/accounts/{id}'
  * Retrieves the account for the provided id
  * The account details are retrieved from database and returned to client
* POST Ledger Transfer: '/ledger/transfer'
  * Client triggers an API call with the transfer payload below:
    * transferId
    * fromAccountId
    * toAccountId
    * amount
  * All transfer requests are handled as atomic transactions to guarantee data quality and accuracy
  * Transfer service automatically generates and attach a transferId 
  * Transfer service propagates the request to ledger microservice which fulfils the transfer request
    * ledger microservice returns the transfer status to the transfer service
    * if ledger service is unavailable, transfer service internally handles teh failure via a circuit breaker 
  * Transfer service handles the Idempotency of transfer requests to prevent duplication of transactions and ensure performance across the services



### DAO Implementation
* JPA Hibernate database interaction implementation
* All write operations are executed atomically inside a transaction
* Enabled liquibase for reliable database versioning and maintenance
* Utilising H2 In-Memory database that enables ease of service spin up locally or via a Docker Container


### Security Implementation
* Spring security with jwt authentication not a requirement for day 1 implementation
* Basic principal authentication (username and password) design
* A Token Generation API to be exposed
* Jwt token validated with a 2 Hours expiry window


### Transfer Service Packaging
* Attached Docker compose script to build the microservice deployment artifact
* Containers expose port 8081:8081 mapping to local port


### Transfer Service References
The service swagger: http://localhost:8081/api/docs