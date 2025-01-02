FROM amazoncorretto:21

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

CMD apt-get update

ENTRYPOINT ["java","-jar","/app.jar"]