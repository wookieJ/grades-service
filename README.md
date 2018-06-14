[![Build Status](https://travis-ci.org/wookieJ/grades-service.svg?branch=master)](https://travis-ci.org/wookieJ/grades-service)
[![codecov](https://codecov.io/gh/wookieJ/grades-service/branch/master/graph/badge.svg)](https://codecov.io/gh/wookieJ/grades-service)
# grades-service
Web API Application to manage students, grades and courses.

## Getting Started

You need maven in version at least 3.3.9 and JDK 8.<br/>
To clone repository use following command:

```
git clone https://github.com/wookieJ/grades-service.git
```
<!--
## Installing

To build and install project use following command:
```
mvn clean install compile
```
-->
<!-- Add manifest and than package command above for install and running section -->

<!--
## Running
After building the application run following command to start it:
```
java -jar target/rest-app.jar
```
Maybe add choosing port option in parameters when starting jar file and add exception and description of it here.
-->

## Screenshots

<p align="center">
  <img width="750" src="../master/screenshots/screen.PNG">
</p>

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
* [Travis](https://travis-ci.org/) - Test and Deploy tool

## Tech Stack
### Backend
* [Grizzly](https://javaee.github.io/grizzly/) - NIO Event Development framework.
* [MongoDB](https://www.mongodb.com/) -  Free and open-source cross-platform document-oriented database program.
  * [Morphia](https://mongodb.github.io/morphia/) -  The Java Object Document Mapper for MongoDB.
* [Jersey](https://jersey.github.io/) -  RESTful Web Services in Java.

### Frontend
* [KnockoutJS](http://knockoutjs.com/) - Simplify dynamic JavaScript UIs with the Model-View-View Model (MVVM) pattern.
* [jQuery](https://jquery.com/) -  Fast, small, and feature-rich JavaScript library.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details
