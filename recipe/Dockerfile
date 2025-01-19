FROM openjdk:17-jdk-alpine
WORKDIR /app
RUN apk --no-cache add curl

# Copy the built JAR file from the builder stage
COPY ./target/*.jar app.jar
EXPOSE 8800

ENTRYPOINT ["java", "-jar", "app.jar"]
