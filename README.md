# Railway Ticket Booking System

Welcome to the Railway Ticket Booking System repository. This project is designed to facilitate the booking of train tickets through a web application. It leverages modern technologies and follows a Pipe and Filters architectural pattern to efficiently process user requests. This README provides an overview of the project structure, technologies used, API endpoints, and how to get started with development.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Overview](#project-overview)
- [Pipe and Filters Architecture](#pipe-and-filters-architecture)
- [REST API Endpoints](#rest-api-endpoints)
- [OAuth2 Verification](#oauth2-verification)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Running the Application](#running-the-application)
    - [Testing API Endpoints](#testing-api-endpoints)
    - [The testing Scenario](#the-testing-scenario)


## Technologies Used

- **Java**: Core programming language used for backend development.
- **Spring Framework**: Leveraged for dependency injection, MVC architecture, and integration with other Spring projects.
- **Spring Boot**: Provides a streamlined development platform for building production-ready applications.
- **Spring Data JPA**: Simplifies data access using the ORM (Object-Relational Mapping) capabilities of JPA (Java Persistence API).
- **Spring Security**: Handles authentication, authorization, and OAuth2 integration for securing API endpoints.
- **Jakarta EE**: Platform for building enterprise-grade applications based on Java EE specifications.
- **GitLab**: Version control system and Git repository hosting service used for collaborative development.
- **Maven**: Build automation tool used for managing dependencies and project build configuration.

## Project Overview

The Railway Ticket Booking System aims to provide a user-friendly platform for booking train tickets. It follows a modular architecture inspired by the Pipe and Filters pattern, which allows for efficient data processing through interconnected components called filters and pipes. Each filter performs a specific task such as filtering connections, calculating prices, or selecting available seats, and passes the processed data through pipes to the next filter in the chain.

## Pipe and Filters Architecture

The application architecture is based on the Pipe and Filters pattern:

- **Pipes**: Implemented as queues that facilitate the flow of data between filters. They ensure that data processing is decoupled and asynchronous, enhancing system responsiveness and scalability.

- **Filters**: Each filter is responsible for a specific operation such as fetching train connections, filtering available seats, calculating ticket prices, and issuing tickets. Filters are designed to be modular and reusable, promoting code maintainability and extensibility.

- **Logic**: Pipe and Filters are singleton threads. They notify each other when data moves in them. The format of data they use is session specific data (here we store information about what user picked like seat, discountType, stations, etc...) and List of objects. More specific ifnormatio ncan be found in [component diagram](EA/Component.jpg).

## REST API Endpoints

The REST API endpoints expose various functionalities of the Railway Ticket Booking System:

- `/rest/search/connections`: Retrieves train connections based on origin, destination, departure time, and date.
- `/rest/seat/train/{trainID}`: Retrieves available seats for a given train.
- `/rest/seat/select/{trainID}{seatNumber}`: Reserves a specific seat in the train.
- `/rest/login`: User login.
- `/rest/search/data`: Fill the database with example data.


## OAuth2 Verification

The Railway Ticket Booking System utilizes OAuth2 for secure authentication and authorization. OAuth2 integration ensures that only authenticated users can access sensitive endpoints such as ticket reservation and purchase. Authentication tokens are issued and validated to maintain the integrity and security of user transactions.

## Getting Started

### Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK) version 11 or higher
- Maven build tool
- Database configuration (refer to `application.properties` for setup)

### Running the Application

To start the Railway Ticket Booking System application:

1. Clone the repository from GitLab.
2. Install Java Development Kit (JDK) version 11 or higher.
3. Configure the database connection settings in `application.properties`.
4. Build the project using Maven: `mvn clean install`.
5. Run the application using Spring Boot: `mvn spring-boot:run`.
6. Access the API endpoints using tools like Postman or integrate them into your frontend application.

### Testing API Endpoints

To test the API endpoints, you can use the Postman collection provided in the docs directory:

1. Import the Postman collection (Railway Ticket Booking System.postman_collection.json) into Postman.

2. Ensure the application is running locally (http://localhost:8080).

3. Use the imported collection to execute requests against the API endpoints (/api/connections, /api/seats, /api/tickets) and verify the responses.


### The testing scenario
This is the ideal order of Postman requests to test the project. Import the Postman request using this [JSON File](Postman/NSS.postman_collection.json).
1. DataCreation
2. Search Connections
3. Register
4. Login
5. Search Connection
6. Get All Seats
7. Get Specific Seat

