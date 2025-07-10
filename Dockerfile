FROM openjdk:19-jdk-slim

WORKDIR /app

COPY . .
RUN ./mvnw clean install

CMD ["java", "-jar", "target/presto-expedition-service-0.0.1-SNAPSHOT.jar"]
