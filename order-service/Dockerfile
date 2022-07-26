FROM openjdk:8-jre-alpine

COPY ["target/order-service-1.0-SNAPSHOT.jar", "order-service.jar"]
EXPOSE 9002

ENTRYPOINT ["java", "-jar", "order-service.jar"]