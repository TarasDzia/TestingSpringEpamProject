FROM openjdk:latest
MAINTAINER Taras Dziadyk <taras_dziadyk@epam.com>

COPY ["target/TestingAppSpring-0.0.1-SNAPSHOT.jar", "TestingAppSpring-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "TestingAppSpring-0.0.1-SNAPSHOT.jar"]

#docker images                           // see all created images
#docker ps                               // see all running containers
#docker build -t {nameOfNewImage} .
#docker run -d -p 8080:8080 {idOfImage}