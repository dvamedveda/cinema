[![Build Status](https://travis-ci.com/dvamedveda/cinema.svg?branch=master)](https://travis-ci.com/dvamedveda/cinema)
[![codecov](https://codecov.io/gh/dvamedveda/cinema/branch/master/graph/badge.svg?token=6JZHOWIMIK)](https://codecov.io/gh/dvamedveda/cinema)


# cinema
Simple MVC Java web application for booking seats in the cinema.
This application developed with layered architecture:
- persistance-layer
- service-layer
- controller-layer
- view-layer

It used DAO/DTO, Singleton templates.

### Application features:

- user can book seat for self, doing payment and leave he own name and telephone number;  
- application supports automatic refreshing state of hall of cinema for other users;  
- user can not book chosen seat, if this seat has been booked by other user early;
- web app supports validation for entered user data;
- web app automatically update/check database state on startup.

### Application screenshots:
- booking seat
![booking](https://github.com/dvamedveda/screenshots/blob/main/cinema/cinema_hall.png?raw=true)  
- payment for booking
![payment](https://github.com/dvamedveda/screenshots/blob/main/cinema/cinema_pay.png?raw=true)  

### Used frameworks, libs, technologies:
- Java, Maven, JUnit, JSP, JSTL, Log4j
- JS, jQuery, Twitter Bootstrap
- Postgresql, JDBC, Flyway