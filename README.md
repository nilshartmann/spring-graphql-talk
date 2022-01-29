# GraphQL Example Application "Beer Advisor"

This repository contains a very simple GraphQL example application, using the following technology stack:

- [graphql-java](https://www.graphql-java.com/)
- [graphql-java-tools](https://www.graphql-java-kickstart.com/tools/)
- [spring-graphql](https://docs.spring.io/spring-graphql/docs/current-SNAPSHOT/reference/html/)
- [Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [React](https://reactjs.org/)
- [TypeScript](https://www.typescriptlang.org/)
- [Apollo GraphQL for React](https://github.com/apollographql/react-apollo)

* Note that not all parts of the application uses the newest APIs as I don't have time to constantly upgrade all dependencies. See `build.gradle` and `package.json`-files for currently used versions.

# Installing and starting

Prerequisites:

- Clone this repository locally
- Port 9000 must be available for the backend
- Port 9020 must be available for the user "microservice" (REST API)
- Port 9080 must be available for the frontend webserver
- You need to have installed Java/JDK 11 for the backend and `yarn` for the frontend build and dependency management (`npm` might also work)

**Step 1: Build and start the user microservice**

1. Open a terminal inside the `userservice` folder
2. Run `./gradlew bootRun` to start the Spring Boot-based REST userservice.
3. The service now should listen on port 9020.

Now start either the `graphql-java`-, `spring-graphql`-based Backend **or** the `graphq-java-tools`-based backend:

**Step 2: Option 1: Build and start the `spring-graphql` backend**

1. Open a terminal inside the `backend-spring-graphql` folder
2. Run `./gradlew bootRun` to start the Spring Boot-based application.
3. The application now should listen on port 9000.

4. Note that the `backend-spring-graphql` backend does not use `DataLoader` currently.

**OR:**

**Step 2: Option 2: Build and start the `graphql-java` backend**

1. Open a terminal inside the `backend` folder
2. Run `./gradlew bootRun` to start the Spring Boot-based application.
3. The application now should listen on port 9000.

**OR:**

**Step 2: Option 3: Build and start the `graphql-java-tools` backend**

1. Open a terminal inside the `backend-tools` folder
2. Run `./gradlew bootRun` to start the Spring Boot-based application.
3. The application now should listen on port 9000.

Note that the `graphql-java-tools`-based backend does not use `DataLoader`s currently.


**Step 3: Install, build and start the frontend (Apollo 3.x)**

1. Open a new terminal inside the `frontend` folder
2. Install the npm packages: `yarn install`
3. Start the web server (depending on the backend you've started): `yarn start:spring-graphql` **OR** `yarn start:graphql-java` **OR** `yarn start:graphql-java-tools`
4. The webserver now should listen on http://localhost:9080

Now everything runs. You can open in your browser:

- http://localhost:9080 for the demo application frontend
- http://localhost:9000 for the [GraphiQL](https://github.com/graphql/graphiql) API Explorer

Enjoy! 💐

# Problems, Questions, Contact

If you have any problems, comments or question, don't hesitate to contact me. You can either open an issue in this repository, find me on [Twitter](https://twitter.com/nilshartmann) or on [some more channels](https://nilshartmann.net/contact/).

# More Examples

Inside [this repository](https://github.com/nilshartmann/graphql-examples) you can find more variations of the Beer Advisor application, developed with different tech stacks.

You can also find my Spring PetClinic fork that works with GraphQL on a Spring-based Server: [spring-petclinic-graphql](https://github.com/spring-petclinic/spring-petclinic-graphql) (note: this uses an old Apollo API).

# Article on GraphQL

I've written a (german) article about GraphQL, React and TypeScript that uses this example application in the iX newspaper. You can read the article [here online](https://www.heise.de/select/ix/2019/6/1909808311106276205) (Note: behind paywall).

You can find even more of my articles, talks and workshops about GraphQL [here](https://nilshartmann.net/tags/GraphQL) (in german only)
