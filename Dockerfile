# Build stage
FROM eclipse-temurin:21-jdk-jammy AS builder

# 1. First copy only the minimal build files needed for dependency resolution
COPY --chown=root:root --chmod=755 gradlew .
COPY --chown=root:root --chmod=755 gradle/wrapper/gradle-wrapper.jar gradle/wrapper/
COPY --chown=root:root --chmod=644 gradle/wrapper/gradle-wrapper.properties gradle/wrapper/
COPY --chown=root:root --chmod=644 build.gradle.kts .
COPY --chown=root:root --chmod=644 settings.gradle.kts .

# 2. Download dependencies as root (temporary)
RUN ./gradlew dependencies --no-daemon

# 3. Copy source code (read-only)
COPY --chown=root:root --chmod=644 src src

# 4. Build application (still as root)
RUN ./gradlew build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy built artifact with root ownership and minimal permissions
COPY --from=builder --chown=root:root --chmod=555 /app/build/libs/*.jar app.jar

# Create non-root user for runtime
RUN groupadd -r spring -g 1001 && \
    useradd -r -u 1001 -g spring spring && \
    # Grant read/execute to spring user while keeping root ownership
    chmod o+rx /app/app.jar && \
    # Create data directory with proper permissions
    mkdir -p /app/data && \
    chown spring:spring /app/data && \
    chmod 750 /app/data

# Runtime configuration
ENV SERVER_PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod

# Switch to non-root user for execution
USER spring

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]