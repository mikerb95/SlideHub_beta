# ═══════════════════════════════════════════════════════════
# SyncSlide — Multi-stage Docker Build
# ═══════════════════════════════════════════════════════════

# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw && ./mvnw dependency:resolve -B
COPY src ./src
RUN ./mvnw package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S syncslide && adduser -S syncslide -G syncslide

COPY --from=build /app/target/*.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD wget -qO- http://localhost:8080/actuator/health || exit 1

USER syncslide

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
