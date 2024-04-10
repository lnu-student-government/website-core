FROM gradle:8.7.0-jdk AS builder

WORKDIR /app

ENV EUREKA_PORT=8761 \
    PORT=8080

ARG GRADLE_OPTS

COPY build.gradle settings.gradle gradlew /app/

COPY gradle /app/gradle

RUN chmod +x /app/gradlew

COPY src /app/src

RUN ./gradlew build ${GRADLE_OPTS} --stacktrace

FROM openjdk:21-slim

WORKDIR /app

COPY --from=builder /app/build/libs/event-service-*.jar /app/event-service.jar

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "/app/event-service.jar"]
