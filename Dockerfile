FROM maven:3.9.8-eclipse-temurin-21 as build

# Установите рабочего каталога
WORKDIR /clinic

# Копирование POM файл и всех исходников
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /clinic

COPY --from=build /clinic/target/clinic-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080