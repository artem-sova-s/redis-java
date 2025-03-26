FROM maven:3.9.6-eclipse-temurin AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src

# build skipping tests
RUN mvn clean package -D skipTests

FROM eclipse-temurin:23-jdk-alpine

# set the workdir for executable
WORKDIR /app

# copy jar file from the builder
COPY --from=builder /builder/target/*.jar app.jar

# expose the port redis is running on
EXPOSE 6379

ENTRYPOINT [ "java", "-jar", "./app.jar" ]

