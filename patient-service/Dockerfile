FROM openjdk:8-jre-alpine

COPY ["target/patient-service-1.0-SNAPSHOT.jar", "patient-service.jar"]
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "patient-service.jar"]