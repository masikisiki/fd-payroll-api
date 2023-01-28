FROM maven:3.8.7-openjdk-18
WORKDIR ./app
COPY pom.xml .
COPY ./src ./
CMD mvn test