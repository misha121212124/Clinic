FROM openjdk:8-jre-alpine

COPY ["target/gateway-service-1.0-SNAPSHOT.jar", "gateway-service.jar"]
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "gateway-service.jar"]