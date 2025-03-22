@echo off
REM Run from the script directory
cd /d %~dp0

REM Compile using Maven
C:\Maven\bin\mvn.cmd -B package -Ddir=target/build-redis-java

REM Run the compiled JAR
java -jar target/build-redis-java/rediska.jar %*
