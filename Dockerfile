FROM maven:3.8.3-openjdk-11-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /libs/atomic_cinema_ru.atomic_cinema_backend-all.jar .
EXPOSE 8080
CMD ["java", "-jar", "/atomic_cinema_ru.atomic_cinema_backend-all.jar"]
