# Stage 1: Build the application
FROM amazoncorretto:17-alpine3.15 AS builder
WORKDIR /app
COPY . .
RUN gradle build # Assuming you use Gradle (adjust for Maven if needed)

# Stage 2: Runner image
FROM amazoncorretto:17-jre-alpine3.15
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
