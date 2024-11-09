# Используем базовый образ с Java
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл сборки (JAR)
COPY build/libs/Pleasure-0.0.1-SNAPSHOT.jar Pleasure.jar

ENV DB_HOST=db
ENV DB_PORT=5432
ENV DB_NAME=mydatabase
ENV DB_USERNAME=myuser
ENV DB_PASSWORD=mypassword

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "Pleasure.jar"]