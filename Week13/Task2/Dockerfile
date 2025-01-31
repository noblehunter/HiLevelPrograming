# Указываем базовый образ с Maven и JDK
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем pom.xml для управления зависимостями
COPY pom.xml .

# Копируем settings.xml для настроек Maven
COPY settings.xml .

# Копируем исходный код
COPY src ./src

# Запускаем сборку проекта с помощью Maven
RUN mvn clean install -Dmaven.test.skip=true

# --- Phase 2: Production Image ---
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]



