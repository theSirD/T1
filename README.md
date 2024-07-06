### Microservices with Controller-Service-Dao architecture

## Architecture
There are three microservices:
1. Microservice for access to cats.
2. Microservice for access to owners.
3. Microservice with external interfaces (endpoints + authorization)
They are all different applications.

## Tech stack
Microservices were created using Java Spring Boot, Hibernate, Spring Data, JPA и Spring 
Security.

Communication between microservices takes place via Kafka
