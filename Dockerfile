FROM openjdk:17-jdk
RUN groupadd --system spring && adduser --system spring --ingroup spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
