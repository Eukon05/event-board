# Event Board

## What is "Event-Board"?
An event management REST API written in Java + Spring Boot.  
I made it to learn about hexagonal architecture and the importance of defining clear boundaries between domains.  
I based my vision on how to implement hex. arch.
on [Tom Hombergs' "buckpal" project](https://github.com/thombergs/buckpal).

## What features does it have?
- User's can register using their favourite oauth2 login provider, thanks to integration with [Keycloak IAM](https://www.keycloak.org/)  
- Every user is able to create an event, consisting of a name, description , address, and a time period that it happens in
- Events can be kept private or made public for others to view
- Events can be searched through thanks to the app implementing the `JPA Specification API` for them
- Users can become friends with each-other, which allows them to send themselves invites to events that they are
  interested in
- The app will only let you invite another user to an event if:
  - You are friends with the user
  - You can access the event (it's public, or you are its host / attendee)
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
It also removes the temptation to use "hacky" solutions, as the domains can't overlap with each-other (you can't access
any part of the `User` domain's code from the `Event` domain, without using a port and an adapter).  
It was also my first project using Gradle, which I fell in love with due to its short and readable syntax.  
I'm really glad I took-on the challenge and expanded my skillset as a Java developer.

## Tech stack
- Java 17 LTS
- Gradle
- Spring 6 + Spring Boot 3
- Spring Security, Validation, Data JPA, OAuth2-Resource-Server
- JUnit + Mockito
- Rest Assured
- Hibernate JPA Modelgen
- Mapstruct
- PostgreSQL
- Keycloak
- SwaggerUI / springdoc-openapi
- Lombok

## How to use

In order to use the API, you'll need to authenticate with your Keycloak server.  
Instructions on how to do it in Postman
are [here](https://sis-cc.gitlab.io/dotstatsuite-documentation/configurations/authentication/token-in-postman/).  
If you're using Swagger UI, simply press the big `Authorize` button and follow the steps listed in the pop-up.

OpenAPI documentation is available after deployment on the URL below:  
`[root]:8080/api/v1/api-docs`  
You can also view interactive Swagger UI here:  
`[root]:8080/api/v1/swagger-ui.html`

## How to deploy

### Deploy a development environment

You can set up a development environment with Docker:

1. Download the `docker` folder from this repository
2. Modify the `.env` file to suit your needs
3. Run `docker compose up` to start up the database and Keycloak
4. Go to [your Keycloak dashboard](http://localhost:8180) and login with credentials from the `.env` file
5. Click on `Create Realm` and select the file `realm-export.json` (it's present in the docker/keycloak folder)

After these steps, you should have a fully functional development environment running on your machine.  
If you've modified the `.env` file, remember to modify `application.yml` file in the source code accordingly.

### Deploy to production

While deploying to production, you need to manually download and install Keycloak on your machine.  
That's because the compose image uses Keycloak in development mode, which, according to Keycloak documentation, is not
secure and shouldn't be used in production.

After your database and Keycloak are up and running, you can use a Docker image of the app to start it up.  
Here's an example docker run command you can use:

```bash
docker run --name eventboard -p 8080:8080 -e SPRING.DATASOURCE.URL=jdbc:postgresql://dburl/dbname -e SPRING.DATASOURCE.USERNAME=dbuser -e SPRING.DATASOURCE.PASSWORD=dbpass -e SPRING.SECURITY.OAUTH2.RESOURCE-SERVER.JWT.ISSUER-URI=keycloakurl/realms/keycloakrealm eukon/eventboard
```

Please make sure to replace parameters in the above command with your database and Keycloak details.  
The image is compatible with AMD64 and ARM64 processors.
