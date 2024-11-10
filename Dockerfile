# Используем базовый образ с Java
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл сборки (JAR)
COPY build/libs/Pleasure-0.0.1-SNAPSHOT.jar Pleasure.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "Pleasure.jar"]