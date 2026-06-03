# Етап 1: Збірка проєкту за допомогою Gradle
FROM gradle:9.4.1-jdk25 AS build
WORKDIR /app
# Копіюємо всі файли проєкту
COPY . .

RUN chmod +x ./gradlew

# Збираємо .jar файл, пропускаючи тести (щоб уникнути помилок через відсутність БД на етапі збірки)
RUN ./gradlew build -x test

RUN rm -f build/libs/*-plain.jar
# Етап 2: Легковаговий контейнер для запуску
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Беремо готовий зібраний .jar з попереднього етапу
COPY --from=build /app/build/libs/*.jar app.jar
# Вказуємо порт, на якому працює Spring Boot
EXPOSE 8080
# Вказуємо команду для старту нашого застосунку
ENTRYPOINT ["java", "-jar", "app.jar"]