FROM openjdk:16-jdk-alpine3.12
COPY target/bankservice-0.0.1-SNAPSHOT.jar /
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/bankservice-0.0.1-SNAPSHOT.jar"]
