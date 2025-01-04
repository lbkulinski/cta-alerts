FROM openjdk:23

WORKDIR /app

COPY /target/cta-alerts-1.0-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "/app/cta-alerts-1.0-SNAPSHOT.jar"]
