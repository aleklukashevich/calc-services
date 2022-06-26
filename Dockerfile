FROM maven:3-adoptopenjdk-15

WORKDIR /api-tests
COPY src /api-tests/src
COPY pom.xml /api-tests
ENTRYPOINT ["mvn", "test"]
