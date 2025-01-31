# Используем OpenJDK 17 для сборки
FROM openjdk:17-jdk-slim AS build

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Копируем исходные файлы проекта в контейнер
COPY . /app

# Устанавливаем рабочую директорию
WORKDIR /app

# Собираем проект с помощью Maven
RUN mvn clean package -DskipTests

# Используем минимальный образ для запуска приложения
FROM openjdk:17-jdk-slim

# Копируем собранный JAR-файл из предыдущего этапа
COPY --from=build /app/target/separation-1.0-SNAPSHOT.jar /app/separation.jar

# Устанавливаем рабочую директорию
WORKDIR /app

# Запускаем приложение
CMD ["java", "-jar", "/app/separation.jar"]