# Event Board

## What is "Event-Board"?
An event management REST API written in Java + Spring Boot.  
I made it to learn about hexagonal architecture and the importance of defining clear boundaries between domains.  
I based my vision on how to implement hex. arch. on [Tom Homberg's "buckpal" project](https://github.com/thombergs/buckpal).

## What features does it have?
- User's can register using their favourite oauth2 login provider, thanks to integration with [Keycloak IAM](https://www.keycloak.org/)  
- Every user is able to create an event, consisting of a name, description , address, and a time period that it happens in
- Events can be kept private or made public for others to view
- Events can be searched through thanks to the app implementing the `JPA Specification API` for them
- Users can become friends with eachother, which allows them to send themselves invites to events that they are interested in
- The app will only let you invite another user to an event if:
  - You are friends with the user
  - You can access the event (it's public or you are it's host / attendee)
  - The user isn't already invited to (or attending) this event (this is to prevent invite spam)
  - The user isn't the host of the event
- Every event can be modified or deleted according to the host's will
- User accounts cannot be deleted, because of Keycloak handling their registration for us (but don't worry, the app only keeps their Keycloak IDs in the DB to allow for the friends functionality to work)

## Important details
- The project features both unit and integration tests for almost every action the user can make:
  - Integration tests spin up a temporary PostgreSQL DB and a Keycloak server using Docker, to allow the tests to be as close to production environment as possible
  - Unit tests rely heavily on Mockito, to make sure we are testing the business logic and not the underlying technologies, such as a particular DB
- User inputs are validated on every request, and if invalid, a list of errors is returned so that the user knows which part of the request needs to be corrected
- I've also included SwaggerUI in the project, which can be accessed on `https://[serverurl]:[serverport]/api/v1/swagger-ui.html`  
  It allows for easy exploration and testing of all available endpoints

## My conclusions
In this particular example, I wouldn't consider sticking with hexagonal architecture worth the hassle. It's a small project, written by a solo developer, and hexagonal architecture makes it way too complex for one person to handle at times.  
Despite this, I now understand the positives that hex. arch. brings to the table, as it forces the developer to write modular, easy to modify code, that doesn't break the entire application when changed.  
It also removes the temptation to use "hacky" solutions, as the domains can't overlap with eachother (you can't access any part of the `User` domain's code from the `Event` domain, without using a port and an adapter).  
It was also my first project using Gradle, which I fell in love with due to it's short and readable syntax.  
I'm really glad I took-on the challange and expanded my skillset as a Java developer.

## Tech stack
- Java 17 LTS
- Gradle
- Spring 6 + Spring Boot 3
- Spring Security, Validation, Data JPA, OAuth2-Resource-Server
- JUnit + Mockito
- Rest Assurred
- Hibernate JPA Modelgen
- Mapstruct
- PostgreSQL
- Keycloak
- SwaggerUI / springdoc-openapi
- Lombok

## Deployment
I will publish a Docker Compose image for easy deployment when I'll finish development of the project.
