FROM maven:3.9.6-eclipse-temurin AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src
COPY build.sh .

# build skipping tests
RUN ./build.sh

FROM eclipse-temurin:23-jdk-alpine

# set the workdir for executable
WORKDIR /app

# copy jar file from the builder
COPY --from=builder /build/target/*.jar app.jar

# expose the port redis is running on
EXPOSE 6379

ENTRYPOINT [ "java", "-jar", "./app.jar" ]

