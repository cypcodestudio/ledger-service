# ledger-service
Payment service ledger microservice to handle payment transaction
### Localhost run steps:
* Run a terminal session with the pwd same as docker-compose.yml file
* once in the same level as the docker-compose file
    * execute docker-compose up
    * the service will create a docker container
    * the container is set to run on port 8081, make sure this port is not already occupied
    * Check service status: http://localhost:8081/actuator/health
    * Check expose apis: http://localhost:8081/swagger-ui/index.html