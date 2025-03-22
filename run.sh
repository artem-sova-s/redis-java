#!/bin/sh

set -e # Exit early if any commands fail

(
  cd "$(dirname "$0")" # Ensure compile steps are run within the repository directory
  mvn -B package -Ddir=target/build-redis-java
)

# run java application
exec java -jar /tmp/codecrafters-build-redis-java/codecrafters-redis.jar "$@"
