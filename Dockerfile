# Stage 1: Build
FROM eclipse-temurin:25-jdk-alpine AS builder
RUN apk add --no-cache curl unzip && \
    curl -fsSL https://services.gradle.org/distributions/gradle-9.4.1-bin.zip -o /tmp/gradle.zip && \
    unzip -q /tmp/gradle.zip -d /opt && \
    ln -s /opt/gradle-9.4.1/bin/gradle /usr/local/bin/gradle
WORKDIR /app
COPY build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon -q
COPY src ./src
RUN gradle clean build -x test --no-daemon -q

# Stage 2: Run
FROM eclipse-temurin:25-jdk-alpine
VOLUME /tmp
EXPOSE 8088
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]