# Етап 1: Збірка проєкту за допомогою Gradle
FROM gradle:9.4.1-jdk25 AS build
WORKDIR /app

# Копіюємо всі файли проєкту
COPY . .

# Надаємо права на виконання скрипта gradlew
RUN chmod +x ./gradlew

# Збираємо .jar файл, пропускаючи тести
RUN ./gradlew build -x test

# Видаляємо зайвий plain.jar, щоб уникнути помилки копіювання
RUN rm -f build/libs/*-plain.jar

# Етап 2: Легковаговий контейнер для запуску (ОНОВЛЕНО ДО JAVA 25)
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Тепер тут буде лише один правильний файл
COPY --from=build /app/build/libs/*.jar app.jar

# Вказуємо порт, на якому працює Spring Boot
EXPOSE 8080

# Вказуємо команду для старту нашого застосунку
ENTRYPOINT ["java", "-jar", "app.jar"]