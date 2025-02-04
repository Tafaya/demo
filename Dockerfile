FROM gradle:jdk17-alpine AS build
CMD ["pwd"]
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17.0.1-jdk-slim
EXPOSE 8080
RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/demo.jar

CMD ["java","-jar","/app/demo.jar"]

