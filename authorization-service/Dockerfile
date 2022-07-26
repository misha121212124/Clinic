FROM openjdk:8-jre-alpine

COPY ["target/authorization-service-1.0-SNAPSHOT.jar", "authorization-service.jar"]
EXPOSE 8999

ENTRYPOINT ["java", "-jar", "authorization-service.jar"]