FROM openjdk:8-jre-alpine

COPY ["target/EurekaServer-1.0-SNAPSHOT.jar", "eureka-service.jar"]
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "eureka-service.jar"]