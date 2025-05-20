# Fase de construcción (usa JDK para compilar)
FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /app

# Copia los archivos de Gradle (para caché eficiente)
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Descarga dependencias primero (caché si no cambian build.gradle.kts)
RUN ./gradlew dependencies --no-daemon

# Copia el código fuente y construye el JAR
COPY src src
RUN ./gradlew build -x test --no-daemon  # Excluye tests para construcción más rápida

# --- Fase de ejecución (usa JRE para ahorrar espacio) ---
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia el JAR desde la fase de construcción
COPY --from=builder /app/build/libs/*.jar app.jar

# Variables de entorno para configuración dinámica (opcional)
ENV SERVER_PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod

# Puerto expuesto (Render lo manejará)
EXPOSE ${SERVER_PORT}

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]