FROM openjdk:17-jdk
RUN groupadd --system spring && adduser --system spring -g spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
