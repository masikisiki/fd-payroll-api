FROM openjdk:17.0-jdk-slim
WORKDIR /app
COPY target/fd-payroll-0.0.1-SNAPSHOT.jar  /app/
EXPOSE 8080
CMD ["java","-jar","-Dspring.profiles.active=prod","fd-payroll-0.0.1-SNAPSHOT.jar "]
