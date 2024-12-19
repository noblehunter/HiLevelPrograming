# Используем базовый образ с OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем исходный код
COPY *.java ./

# Компилируем Java код
RUN javac *.java

# Команда для запуска приложения
CMD ["java", "Main"]