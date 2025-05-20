FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN ./gradlew dependencies --no-daemon

COPY src src
RUN ./gradlew build -x test --no-daemon  # Excluye tests para construcción más rápida

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENV SERVER_PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]