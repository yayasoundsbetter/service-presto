FROM openjdk:19-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x ./mvnw

RUN ./mvnw clean install

CMD ["java", "-jar", "target/presto-expedition-service-1.0.0.jar"]
