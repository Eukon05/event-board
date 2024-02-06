FROM amazoncorretto:17.0.5

ARG JAR_FILE=*.jar
ADD build/libs/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
