# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Only copy the pom.xml initially to leverage Docker cache
COPY pom.xml .
# Install dependencies
RUN mvn dependency:go-offline
# Copy your src directory after resolving dependencies to utilize cache when src files change
COPY src ./src
# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Copy the JAR file from the build stage to the run stage
COPY --from=build /app/target/MazeSolver-0.0.1-SNAPSHOT.jar MazeSolver.jar
# Expose the port the application runs on
EXPOSE 8080
# Command to run the application
ENTRYPOINT ["java", "-jar", "MazeSolver.jar"]
